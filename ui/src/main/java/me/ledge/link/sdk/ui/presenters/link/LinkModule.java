package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.FinancialAccountSelectorModule;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class LinkModule extends LedgeBaseModule {

    public LinkModule(Activity activity) {
        super(activity);
    }
    private boolean mSkipDisclaimers;
    private boolean mIsUserLoggedIn;

    @Override
    public void initialModuleSetup() {
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getSkipLinkDisclaimer())
                .exceptionally(ex -> {
                    showLinkDisclaimers();
                    return null;
                })
                .thenAccept(this::skipLinkDisclaimerRetrieved);
    }

    private void showLinkDisclaimers() {
        TermsModule mTermsModule = TermsModule.getInstance(this.getActivity());
        mTermsModule.onFinish = this::showLoanInfo;
        mTermsModule.onBack = this::showHomeActivity;
        startModule(mTermsModule);
    }

    private void showLoanInfo() {
        LoanInfoModule loanInfoModule = LoanInfoModule.getInstance(this.getActivity());
        if(mIsUserLoggedIn) {
            loanInfoModule.onGetOffers = this::showOffersList;
            loanInfoModule.onFinish = this::showOffersList;
            loanInfoModule.onUpdateProfile = () -> startUserDataCollectorModule(true);
        }
        else {
            loanInfoModule.onFinish = this::showUserDataCollector;
        }
        if(mSkipDisclaimers) {
            loanInfoModule.onBack = this::showHomeActivity;
        }
        else {
            loanInfoModule.onBack = this::showLinkDisclaimers;
        }
        startModule(loanInfoModule);
    }

    private void showUserDataCollector() {
        startUserDataCollectorModule(false);
    }

    private void startUserDataCollectorModule(boolean updateProfile) {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onUserNotLoggedIn = null;
        userDataCollectorModule.onFinish = this::showOffersList;
        userDataCollectorModule.onBack = this::showLoanInfo;
        userDataCollectorModule.isUpdatingProfile = updateProfile;
        startModule(userDataCollectorModule);
    }

    private void showOffersList() {
        mIsUserLoggedIn = true;
        LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(this.getActivity());
        loanApplicationModule.onUpdateUserProfile = () -> startUserDataCollectorModule(true);
        loanApplicationModule.onBack = this::showLoanInfo;
        loanApplicationModule.onSelectFundingAccount = this::showFundingAccountSelector;
        startModule(loanApplicationModule);
    }

    private void showFundingAccountSelector() {
        FinancialAccountSelectorModule financialAccountSelectorModule = FinancialAccountSelectorModule.getInstance(this.getActivity());
        financialAccountSelectorModule.onBack = this::showOffersList;
        startModule(financialAccountSelectorModule);
    }

    private void showHomeActivity() {
        startActivity(this.getActivity().getClass());
    }

    private void skipLinkDisclaimerRetrieved(boolean skipLinkDisclaimer) {
        mSkipDisclaimers = skipLinkDisclaimer;
        askDataCollectorIfUserIsLoggedIn();
    }

    private void askDataCollectorIfUserIsLoggedIn() {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onFinish = this::showOrSkipDisclaimers;
        userDataCollectorModule.onUserNotLoggedIn = () -> {
            mIsUserLoggedIn = false;
            showOrSkipDisclaimers();
        };
        userDataCollectorModule.onUserIsLoggedIn = () -> {
            mIsUserLoggedIn = true;
            showOrSkipDisclaimers();
        };
        startModule(userDataCollectorModule);
    }

    private void showOrSkipDisclaimers() {
        if(mSkipDisclaimers) {
            showLoanInfo();
        }
        else {
            showLinkDisclaimers();
        }
    }
}
