package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import android.app.Activity;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;

import me.ledge.link.api.utils.loanapplication.LoanApplicationAccountType;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.activities.fundingaccountselector.DisplayCardActivity;
import me.ledge.link.sdk.ui.activities.fundingaccountselector.EnableAutoPayActivity;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.FinancialAccountSelectorModule;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.workflow.ModuleManager;

/**
 * Created by adrian on 29/12/2016.
 */

public class FundingAccountSelectorModule extends LedgeBaseModule
        implements EnableAutoPayDelegate, DisplayCardDelegate {

    private static FundingAccountSelectorModule instance;
    private FinancialAccountVo mSelectedFinancialAccount;
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

    private void onFinancialAccountSelected(FinancialAccountVo selectedFinancialAccount) {
        mSelectedFinancialAccount = selectedFinancialAccount;
        setFundingAccount();
    }

    private void showEnableAutoPayScreen() {
        ModuleManager.getInstance().setModule(new WeakReference<>(this));
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
    public FinancialAccountVo getFinancialAccount() {
        return mSelectedFinancialAccount;
    }

    @Override
    public void displayCardOnBackPressed() {
        showEnableAutoPayScreen();
    }

    @Override
    public Card getCard() {
        return (Card) mSelectedFinancialAccount;
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
        request.accountId = mSelectedFinancialAccount.mAccountId;
        request.accountType = LoanApplicationAccountType.FUNDING;

        String applicationId = LoanStorage.getInstance().getCurrentLoanApplication().id;
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.setApplicationAccount(request, applicationId);
    }

    /**
     * Called when the set funding account response has been received
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(LoanApplicationDetailsResponseVo response) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        showEnableAutoPayScreen();
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

    }
}