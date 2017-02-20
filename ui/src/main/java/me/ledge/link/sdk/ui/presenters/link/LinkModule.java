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
        LoanInfoModule mLoanInfoModule = LoanInfoModule.getInstance(this.getActivity());
        mLoanInfoModule.onFinish = this::showUserDataCollector;
        if(mSkipDisclaimers) {
            mLoanInfoModule.onBack = this::showHomeActivity;
        }
        else {
            mLoanInfoModule.onBack = this::showLinkDisclaimers;
        }
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
        mLoanApplicationModule.onSelectFundingAccount = this::showFundingAccountSelector;
        startModule(mLoanApplicationModule);
    }

    private void showFundingAccountSelector() {
        FinancialAccountSelectorModule mFinancialAccountSelectorModule = FinancialAccountSelectorModule.getInstance(this.getActivity());
        mFinancialAccountSelectorModule.onBack = this::showOffersList;
        startModule(mFinancialAccountSelectorModule);
    }

    private void showHomeActivity() {
        startActivity(this.getActivity().getClass());
    }

    public void skipLinkDisclaimerRetrieved(boolean skipLinkDisclaimer) {
        mSkipDisclaimers = skipLinkDisclaimer;
        if(skipLinkDisclaimer) {
            showLoanInfo();
        }
        else {
            showLinkDisclaimers();
        }
    }
}
