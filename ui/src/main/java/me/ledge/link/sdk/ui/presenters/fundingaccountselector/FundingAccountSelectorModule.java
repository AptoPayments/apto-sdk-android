package me.ledge.link.sdk.ui.presenters.fundingaccountselector;

import android.app.Activity;

import java.lang.ref.WeakReference;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.activities.fundingaccountselector.DisplayCardActivity;
import me.ledge.link.sdk.ui.activities.fundingaccountselector.EnableAutoPayActivity;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.FinancialAccountSelectorModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class FundingAccountSelectorModule extends LedgeBaseModule
        implements EnableAutoPayDelegate, DisplayCardDelegate {

    private static FundingAccountSelectorModule instance;
    public Command onBack;
    private FinancialAccountVo mSelectedFinancialAccount;

    public static synchronized FundingAccountSelectorModule getInstance(Activity activity) {
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
        FinancialAccountSelectorModule financialAccountSelectorModule = FinancialAccountSelectorModule.getInstance(this.getActivity());
        financialAccountSelectorModule.onBack = this.onBack;
        financialAccountSelectorModule.onFinish = () -> onFinancialAccountSelected(financialAccountSelectorModule.getFinancialAccount());
        startModule(financialAccountSelectorModule);
    }

    private void onFinancialAccountSelected(FinancialAccountVo selectedFinancialAccount) {
        ModuleManager.getInstance().setModule(new WeakReference<>(this));
        mSelectedFinancialAccount = selectedFinancialAccount;
        showEnableAutoPayScreen();
    }

    private void showEnableAutoPayScreen() {
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
}