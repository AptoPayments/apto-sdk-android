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
    private boolean mUserHasAllRequiredData;

    @Override
    public void initialModuleSetup() {
        mUserHasAllRequiredData = false;
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
        mTermsModule.onFinish = this::showOrSkipLoanInfo;
        mTermsModule.onBack = this::showHomeActivity;
        startModule(mTermsModule);
    }

    private void showOrSkipLoanInfo() {
        if (isLoanInfoRequired()) {
            showLoanInfo();
        } else {
            skipLoanInfo();
        }
    }

    private void showLoanInfoOrBack() {
        if (isLoanInfoRequired()) {
            showLoanInfo();
        } else {
            if(mSkipDisclaimers) {
                showHomeActivity();
            }
            else {
                showLinkDisclaimers();
            }
        }
    }

    private boolean isLoanInfoRequired() {
        boolean shouldSkipLoanAmount = ConfigStorage.getInstance().getSkipLoanAmount();
        boolean shouldSkipLoanPurpose = ConfigStorage.getInstance().getSkipLoanPurpose();
        return !shouldSkipLoanAmount || !shouldSkipLoanPurpose;
    }

    private void showLoanInfo() {
        LoanInfoModule loanInfoModule = LoanInfoModule.getInstance(this.getActivity());
        loanInfoModule.userHasAllRequiredData = mUserHasAllRequiredData;
        if(mUserHasAllRequiredData) {
            loanInfoModule.onGetOffers = this::showOffersList;
            loanInfoModule.onFinish = this::showOffersList;
            loanInfoModule.onUpdateProfile = () -> startUserDataCollectorModule(true);
        }
        else {
            loanInfoModule.onGetOffers = null;
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

    private void skipLoanInfo() {
        if(mUserHasAllRequiredData) {
            showOffersList();
        }
        else {
            showUserDataCollector();
        }
    }

    private void showUserDataCollector() {
        startUserDataCollectorModule(false);
    }

    private void startUserDataCollectorModule(boolean updateProfile) {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onUserHasAllRequiredData = null;
        userDataCollectorModule.onFinish = this::showOffersList;
        userDataCollectorModule.onBack = this::showLoanInfoOrBack;
        userDataCollectorModule.isUpdatingProfile = updateProfile;
        startModule(userDataCollectorModule);
    }

    private void showOffersList() {
        mUserHasAllRequiredData = true;
        LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(this.getActivity());
        loanApplicationModule.onUpdateUserProfile = () -> startUserDataCollectorModule(true);
        loanApplicationModule.onBack = this::showOrSkipLoanInfo;
        loanApplicationModule.onSelectFundingAccount = this::showFundingAccountSelector;
        startModule(loanApplicationModule);
    }

    private void showFundingAccountSelector() {
        FinancialAccountSelectorModule financialAccountSelectorModule = FinancialAccountSelectorModule.getInstance(this.getActivity());
        financialAccountSelectorModule.onBack = this::showOffersList;
        startModule(financialAccountSelectorModule);
    }

    private void showHomeActivity() {
        Activity mainActivity = this.getActivity();
        mainActivity.finish();
        mainActivity.startActivity(mainActivity.getIntent());
    }

    private void skipLinkDisclaimerRetrieved(boolean skipLinkDisclaimer) {
        mSkipDisclaimers = skipLinkDisclaimer;
        askDataCollectorIfUserHasAllRequiredData();
    }

    private void askDataCollectorIfUserHasAllRequiredData() {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onFinish = this::showOrSkipDisclaimers;
        userDataCollectorModule.onUserDoesNotHaveAllRequiredData = () -> {
            mUserHasAllRequiredData = false;
            showOrSkipDisclaimers();
        };
        userDataCollectorModule.onUserHasAllRequiredData = () -> {
            mUserHasAllRequiredData = true;
            showOrSkipDisclaimers();
        };
        startModule(userDataCollectorModule);
    }

    private void showOrSkipDisclaimers() {
        if(mSkipDisclaimers) {
            showOrSkipLoanInfo();
        }
        else {
            showLinkDisclaimers();
        }
    }
}
