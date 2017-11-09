package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.app.Activity;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.utils.loanapplication.LoanApplicationActionId;
import me.ledge.link.api.utils.loanapplication.LoanApplicationStatus;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.sdk.storages.ConfigStorage.OffersListStyle;
import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
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

/**
 * Created by adrian on 29/12/2016.
 */

public class LoanApplicationModule extends LedgeBaseModule
        implements IntermediateLoanApplicationDelegate, AddDocumentsListDelegate,
        LoanAgreementDelegate, OffersListDelegate, LoanApplicationSummaryDelegate {
    private static LoanApplicationModule mInstance;
    public Command onUpdateUserProfile;
    public Command onBack;
    public Command onSelectFundingAccount;
    public OffersListStyle mOffersListStyle;

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
                .thenAccept((style) -> {
                    mOffersListStyle = style;
                    showOffers();
                });
    }

    private void showOffers() {
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
    public void onApplicationReceived() {
        LoanApplicationDetailsResponseVo loanApplication = LoanStorage.getInstance().getCurrentLoanApplication();
        if (loanApplication != null) {
            if (loanApplication.status.equals(LoanApplicationStatus.PENDING_BORROWER_ACTION)) {
                if (loanApplication.required_actions.total_count > 0 && loanApplication.required_actions.data[0].action.equals(LoanApplicationActionId.SELECT_FUNDING_ACCOUNT)) {
                    onSelectFundingAccount.execute();
                    return;
                }
            }
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
