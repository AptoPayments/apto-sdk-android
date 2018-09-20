package com.shiftpayments.link.sdk.ui.presenters.fundingaccountselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationAccountType;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.fundingaccountselector.DisplayCardActivity;
import com.shiftpayments.link.sdk.ui.activities.fundingaccountselector.EnableAutoPayActivity;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.FinancialAccountSelectorModule;
import com.shiftpayments.link.sdk.ui.storages.LoanStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;

/**
 * Created by adrian on 29/12/2016.
 */

public class FundingAccountSelectorModule extends ShiftBaseModule
        implements EnableAutoPayDelegate, DisplayCardDelegate {

    private static FundingAccountSelectorModule instance;
    private static SelectFundingAccountConfigurationVo mConfig;

    public static synchronized FundingAccountSelectorModule getInstance(Activity activity, Command onFinish, Command onBack, SelectFundingAccountConfigurationVo config) {
        mConfig = config;
        if (instance == null) {
            instance = new FundingAccountSelectorModule(activity, onFinish, onBack);
        }
        return instance;
    }

    private FundingAccountSelectorModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
    }

    @Override
    public void initialModuleSetup() {
        showFinancialAccountSelector();
    }

    private void showFinancialAccountSelector() {
        FinancialAccountSelectorModule financialAccountSelectorModule = FinancialAccountSelectorModule.getInstance(this.getActivity(), this::onFinancialAccountSelected, this.onBack, mConfig);
        startModule(financialAccountSelectorModule);
    }

    private void onFinancialAccountSelected() {
        setFundingAccount(UserStorage.getInstance().getSelectedFinancialAccountId());
    }

    private void showEnableAutoPayScreen() {
        ModuleManager.getInstance().setModule(new SoftReference<>(this));
        startActivity(EnableAutoPayActivity.class);
    }

    private void showDisplayCardScreen() {
        startActivity(DisplayCardActivity.class);
    }

    @Override
    public void primaryButtonPressed() {
        showDisplayCardScreen();
    }

    @Override
    public void secondaryButtonPressed() {
        // TODO
    }

    @Override
    public void autoPayOnBackPressed() {
        showFinancialAccountSelector();
    }

    @Override
    public String getFinancialAccountId() {
        return UserStorage.getInstance().getSelectedFinancialAccountId();
    }

    @Override
    public void displayCardOnBackPressed() {
        showEnableAutoPayScreen();
    }

    @Override
    public void displayCardPrimaryButtonPressed() {
        // TODO
    }

    @Override
    public void displayCardSecondaryButtonPressed() {
        // TODO
    }

    private void setFundingAccount(String accountId) {
        ApplicationAccountRequestVo request = new ApplicationAccountRequestVo();
        request.accountId = accountId;
        request.accountType = LoanApplicationAccountType.FUNDING;
        request.applicationId = LoanStorage.getInstance().getCurrentLoanApplication().id;

        ShiftSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.setApplicationAccount(request);
        showLoading(true);
    }

    /**
     * Called when the set funding account response has been received
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(LoanApplicationDetailsResponseVo response) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        showEnableAutoPayScreen();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        ApiErrorUtil.showErrorMessage(error, getActivity());
    }
}