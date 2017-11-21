package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;
import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationSummaryActivity;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.presenters.offers.OffersListDelegate;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.vos.ApplicationVo;
import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.workflow.WorkflowModule;
import me.ledge.link.sdk.ui.workflow.WorkflowObject;

import static me.ledge.link.sdk.sdk.LedgeLinkSdk.getApiWrapper;

/**
 * Created by adrian on 29/12/2016.
 */

public class LoanApplicationModule extends LedgeBaseModule
        implements IntermediateLoanApplicationDelegate, AddDocumentsListDelegate,
        LoanAgreementDelegate, OffersListDelegate, LoanApplicationSummaryDelegate {
    private static LoanApplicationModule mInstance;
    public Command onUpdateUserProfile;
    private WorkflowModule mWorkflowModule;

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
}
