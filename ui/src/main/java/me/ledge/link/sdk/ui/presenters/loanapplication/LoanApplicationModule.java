package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.app.Activity;

import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import me.ledge.link.sdk.ui.models.loanapplication.documents.AddDocumentsListModel;
import me.ledge.link.sdk.ui.presenters.offers.OffersListDelegate;

/**
 * Created by adrian on 29/12/2016.
 */

public class LoanApplicationModule extends LedgeBaseModule
        implements IntermediateLoanApplicationDelegate, AddDocumentsListDelegate,
        LoanAgreementDelegate, OffersListDelegate {
    private static LoanApplicationModule mInstance;
    public Command onUpdateUserProfile;
    public Command onBack;

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
        startActivity(OffersListActivity.class);
    }

    @Override
    public void showNext(IntermediateLoanApplicationModel model) {
        startNextActivity(model);
    }

    @Override
    public void showPrevious(IntermediateLoanApplicationModel model) {
        startPreviousActivity(model);
    }

    @Override
    public void onInfoPressed() {
        startActivity(LedgeLinkUi.getHandlerConfiguration().getApplicationsListActivity());
    }

    @Override
    public void showNext(AddDocumentsListModel model) {
        startNextActivity(model);
    }

    @Override
    public void showPrevious(AddDocumentsListModel model) {
        startPreviousActivity(model);
    }

    @Override
    public void showNext(LoanAgreementModel model) {
        startNextActivity(model);
    }

    @Override
    public void showPrevious(LoanAgreementModel model) {
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
        startActivity(IntermediateLoanApplicationActivity.class);
    }

    private void startNextActivity(ActivityModel model) {
        startActivity(model.getNextActivity(this.getActivity()));
    }

    private void startPreviousActivity(ActivityModel model) {
        startActivity(model.getPreviousActivity(this.getActivity()));
    }
}
