package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class LinkModule extends LedgeBaseModule {

    public LinkModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        // TODO: link disclaimers should be optional
        showLinkDisclaimers();
    }

    private void showLinkDisclaimers() {
        TermsModule mTermsModule = TermsModule.getInstance(this.getActivity());
        mTermsModule.onFinish = this::showLoanInfo;
        mTermsModule.onBack = this::showHomeActivity;
        startModule(mTermsModule);
    }

    private void showLoanInfo() {
        LoanInfoModule mLoanInfoModule = LoanInfoModule.getInstance(this.getActivity());
        mLoanInfoModule.onFinish = this::showUserDataCollector;
        mLoanInfoModule.onBack = this::showLinkDisclaimers;
        startModule(mLoanInfoModule);
    }

    private void showUserDataCollector() {
        UserDataCollectorModule mUserDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        mUserDataCollectorModule.onFinish = this::showOffersList;
        mUserDataCollectorModule.onBack = this::showLoanInfo;
        startModule(mUserDataCollectorModule);
    }

    private void showOffersList() {
        LoanApplicationModule mLoanApplicationModule = LoanApplicationModule.getInstance(this.getActivity());
        mLoanApplicationModule.onUpdateUserProfile = this::showUserDataCollector;
        mLoanApplicationModule.onBack = this::showLoanInfo;
        startModule(mLoanApplicationModule);
    }

    private void showHomeActivity() {
        startActivity(this.getActivity().getClass());
    }
}
