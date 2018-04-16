package com.shift.link.sdk.ui.presenters.loanapplication;

import android.app.Activity;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import com.shift.link.sdk.sdk.storages.ConfigStorage;
import com.shift.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import com.shift.link.sdk.ui.activities.loanapplication.LoanApplicationSummaryActivity;
import com.shift.link.sdk.ui.activities.loanapplication.SelectLoanApplicationListActivity;
import com.shift.link.sdk.ui.activities.offers.OffersListActivity;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import com.shift.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import com.shift.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import com.shift.link.sdk.ui.models.loanapplication.SelectLoanApplicationModel;
import com.shift.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import com.shift.link.sdk.ui.models.offers.OfferSummaryModel;
import com.shift.link.sdk.ui.presenters.offers.OffersListDelegate;
import com.shift.link.sdk.ui.storages.LoanStorage;
import com.shift.link.sdk.ui.vos.ApplicationVo;
import com.shift.link.sdk.ui.workflow.Command;
import com.shift.link.sdk.ui.workflow.LedgeBaseModule;
import com.shift.link.sdk.ui.workflow.ModuleManager;
import com.shift.link.sdk.ui.workflow.WorkflowModule;
import com.shift.link.sdk.ui.workflow.WorkflowObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;

import static com.shift.link.sdk.sdk.LedgeLinkSdk.getApiWrapper;

/**
 * Created by adrian on 29/12/2016.
 */

public class LoanApplicationModule extends LedgeBaseModule
        implements IntermediateLoanApplicationDelegate, AddDocumentsListDelegate,
        LoanAgreementDelegate, OffersListDelegate, LoanApplicationSummaryDelegate,
        SelectLoanApplicationListDelegate {
    private static LoanApplicationModule mInstance;
    public Command onUpdateUserProfile;
    public Command onStartNewApplication;
    private WorkflowModule mWorkflowModule;
    private LoanApplicationsSummaryListResponseVo mApplicationList;

    public static synchronized LoanApplicationModule getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new LoanApplicationModule(activity);
        }
        return mInstance;
    }

    private LoanApplicationModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getOffersListStyle())
                .thenAccept((style) -> showOffers());
    }

    private void showOffers() {
        ModuleManager.getInstance().setModule(new WeakReference<>(this));
        startActivity(OffersListActivity.class);
    }

    @Override
    public void intermediateLoanApplicationShowNext(IntermediateLoanApplicationModel model) {
        startNextActivity(model);
    }

    @Override
    public void intermediateLoanApplicationShowPrevious(IntermediateLoanApplicationModel model) {
        startPreviousActivity(model);
    }

    @Override
    public void addDocumentsListShowNext(AddDocumentsListModel model) {
        startNextActivity(model);
    }

    @Override
    public void addDocumentsListShowPrevious(AddDocumentsListModel model) {
        startPreviousActivity(model);
    }

    @Override
    public void loanAgreementShowNext(LoanAgreementModel model) {
        startNextActivity(model);
    }

    @Override
    public void loanAgreementShowPrevious(LoanAgreementModel model) {
        startPreviousActivity(model);
    }

    public void onUpdateUserProfile() {
        onUpdateUserProfile.execute();
    }

    @Override
    public void onOffersListBackPressed() {
        onBack.execute();
    }

    @Override
    public void onApplicationReceived(ApplicationVo application) {
        LoanApplicationDetailsResponseVo loanApplication = LoanStorage.getInstance().getCurrentLoanApplication();
        if (loanApplication != null) {
            mWorkflowModule = new WorkflowModule(this.getActivity(), application, this::getApplicationStatus);
            mWorkflowModule.onBack = this::showOffers;
            mWorkflowModule.onFinish = this.onFinish;
            mWorkflowModule.initialModuleSetup();
            return;
        }
        startActivity(IntermediateLoanApplicationActivity.class);
    }

    @Override
    public void onConfirmationRequired(OfferSummaryModel offer) {
        LoanStorage.getInstance().setSelectedOffer(offer.getOffer());
        startActivity(LoanApplicationSummaryActivity.class);
    }

    @Override
    public void onUpdateLoan() {
        onBack.execute();
    }

    private ApplicationVo getApplicationStatus(WorkflowObject currentObject) {
        if(!(currentObject instanceof ApplicationVo)) {
            throw new RuntimeException("Current workflow object is not an application!");
        }
        CompletableFuture<LoanApplicationDetailsResponseVo> future = CompletableFuture.supplyAsync(() -> {
            try {
                return getApiWrapper().getApplicationStatus(currentObject.workflowObjectId);
            } catch (ApiException e) {
                throw new CompletionException(e);
            }
        });
        try {
            LoanApplicationDetailsResponseVo applicationStatus = future.get();
            LoanStorage.getInstance().setCurrentLoanApplication(applicationStatus);
            return new ApplicationVo(applicationStatus.id, applicationStatus.next_action);
        } catch (InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
            throw new CompletionException(e);
        }
    }

    private void startNextActivity(ActivityModel model) {
        startActivity(model.getNextActivity(this.getActivity()));
    }

    private void startPreviousActivity(ActivityModel model) {
        startActivity(model.getPreviousActivity(this.getActivity()));
    }

    @Override
    public void loanApplicationSummaryShowPrevious(LoanApplicationSummaryModel model) {
        showOffers();
    }

    public void continueApplication(String applicationId) {
        ApplicationVo application = this.getApplicationStatus(new ApplicationVo(applicationId, null));
        this.onApplicationReceived(application);
    }

    public void startLoanApplicationSelector(LoanApplicationsSummaryListResponseVo loanApplicationSummaryList) {
        mApplicationList = loanApplicationSummaryList;
        ModuleManager.getInstance().setModule(new WeakReference<>(this));
        startActivity(SelectLoanApplicationListActivity.class);
    }

    @Override
    public void selectLoanApplicationListOnBackPressed() {
        onBack.execute();
    }

    @Override
    public void newApplicationPressed() {
        onStartNewApplication.execute();
    }

    @Override
    public void applicationSelected(SelectLoanApplicationModel model) {
        continueApplication(model.getApplicationId());
    }

    @Override
    public LoanApplicationsSummaryListResponseVo getLoanApplicationsSummaryList() {
        return mApplicationList;
    }
}
