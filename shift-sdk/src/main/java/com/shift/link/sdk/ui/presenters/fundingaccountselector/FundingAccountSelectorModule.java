package com.shift.link.sdk.ui.presenters.fundingaccountselector;

import android.app.Activity;
import android.widget.Toast;

import com.shift.link.sdk.api.utils.loanapplication.LoanApplicationAccountType;
import com.shift.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import com.shift.link.sdk.api.vos.responses.ApiErrorVo;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shift.link.sdk.sdk.ShiftLinkSdk;
import com.shift.link.sdk.ui.ShiftPlatform;
import com.shift.link.sdk.ui.activities.fundingaccountselector.DisplayCardActivity;
import com.shift.link.sdk.ui.activities.fundingaccountselector.EnableAutoPayActivity;
import com.shift.link.sdk.ui.presenters.financialaccountselector.FinancialAccountSelectorModule;
import com.shift.link.sdk.ui.storages.LoanStorage;
import com.shift.link.sdk.ui.workflow.ModuleManager;
import com.shift.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;

/**
 * Created by adrian on 29/12/2016.
 */

public class FundingAccountSelectorModule extends ShiftBaseModule
        implements EnableAutoPayDelegate, DisplayCardDelegate {

    private static FundingAccountSelectorModule instance;
    private String mSelectedFinancialAccountId;
    private static SelectFundingAccountConfigurationVo mConfig;

    public static synchronized FundingAccountSelectorModule getInstance(Activity activity, SelectFundingAccountConfigurationVo config) {
        mConfig = config;
        if (instance == null) {
            instance = new FundingAccountSelectorModule(activity);
        }
        return instance;
    }

    private FundingAccountSelectorModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        showFinancialAccountSelector();
    }

    private void showFinancialAccountSelector() {
        FinancialAccountSelectorModule financialAccountSelectorModule = FinancialAccountSelectorModule.getInstance(this.getActivity(), mConfig);
        financialAccountSelectorModule.onBack = this.onBack;
        financialAccountSelectorModule.onFinish = this::onFinancialAccountSelected;
        startModule(financialAccountSelectorModule);
    }

    private void onFinancialAccountSelected(String selectedFinancialAccountId) {
        mSelectedFinancialAccountId = selectedFinancialAccountId;
        setFundingAccount();
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
        return mSelectedFinancialAccountId;
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

    private void setFundingAccount() {
        ApplicationAccountRequestVo request = new ApplicationAccountRequestVo();
        request.accountId = mSelectedFinancialAccountId;
        request.accountType = LoanApplicationAccountType.FUNDING;

        String applicationId = LoanStorage.getInstance().getCurrentLoanApplication().id;
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.setApplicationAccount(request, applicationId);
        showLoading(true);
    }

    /**
     * Called when the set funding account response has been received
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(LoanApplicationDetailsResponseVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        showEnableAutoPayScreen();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
    }
}