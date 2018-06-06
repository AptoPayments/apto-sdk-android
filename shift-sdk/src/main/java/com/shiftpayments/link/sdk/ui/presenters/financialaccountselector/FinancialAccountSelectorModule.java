package com.shiftpayments.link.sdk.ui.presenters.financialaccountselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.financialaccountselector.AddBankAccountActivity;
import com.shiftpayments.link.sdk.ui.activities.financialaccountselector.AddCardActivity;
import com.shiftpayments.link.sdk.ui.activities.financialaccountselector.AddFinancialAccountListActivity;
import com.shiftpayments.link.sdk.ui.activities.financialaccountselector.IntermediateFinancialAccountListActivity;
import com.shiftpayments.link.sdk.ui.activities.financialaccountselector.SelectFinancialAccountListActivity;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by adrian on 29/12/2016.
 */

public class FinancialAccountSelectorModule extends ShiftBaseModule
        implements AddFinancialAccountListDelegate, AddCardDelegate, AddBankAccountDelegate,
        SelectFinancialAccountListDelegate, IntermediateFinancialAccountListDelegate {

    private static FinancialAccountSelectorModule instance;

    private SelectFundingAccountConfigurationVo mConfig;
    public SelectFinancialAccountCallback onFinish;

    public static synchronized FinancialAccountSelectorModule getInstance(Activity activity, SelectFundingAccountConfigurationVo config) {
        if (instance == null) {
            instance = new FinancialAccountSelectorModule(activity, config);
        }
        return instance;
    }

    private FinancialAccountSelectorModule(Activity activity, SelectFundingAccountConfigurationVo config) {
        super(activity);
        mConfig = config;
    }

    @Override
    public void initialModuleSetup() {
        startActivity(IntermediateFinancialAccountListActivity.class);
    }

    @Override
    public void addFinancialAccountListOnBackPressed() {
        onBack.execute();
    }

    @Override
    public void addCard() {
        startActivity(AddCardActivity.class);
    }

    @Override
    public void addBankAccount() {
        startActivity(AddBankAccountActivity.class);
    }

    @Override
    public void virtualCardIssued(Card virtualCard) {
        onFinancialAccountSelected(virtualCard.mAccountId);
    }

    @Override
    public void cardAdded(Card card) {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.addCard(card);
        showLoading(true);
    }

    @Override
    public void addCardOnBackPressed() {
        showAddFinancialAccountListSelector();
    }

    @Override
    public void addBankAccountOnBackPressed() {
        showAddFinancialAccountListSelector();
    }

    @Override
    public void bankAccountLinked(String token) {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        AddBankAccountRequestVo request = new AddBankAccountRequestVo();
        request.publicToken = token;
        ShiftPlatform.addBankAccount(request);
    }

    @Override
    public void selectFinancialAccountListOnBackPressed() {
        onBack.execute();
    }

    @Override
    public void addAccountPressed() {
        showAddFinancialAccountListSelector();
    }

    @Override
    public void accountSelected(SelectFinancialAccountModel model) {
        onFinancialAccountSelected(model.getFinancialAccount().mAccountId);
    }

    @Override
    public void onIntermediateFinancialAccountListBackPressed() {
        onBack.execute();
    }

    @Override
    public void noFinancialAccountsReceived() {
        showAddFinancialAccountListSelector();
    }

    @Override
    public void financialAccountsReceived(DataPointList financialAccounts) {
        if(!ModuleManager.getInstance().getCurrentModule().equals(this)) {
            return;
        }
        DataPointList baseUserData = UserStorage.getInstance().getUserData();
        List<DataPointVo> financialAccountsList = financialAccounts.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount);
        for(DataPointVo financialAccount : financialAccountsList) {
            baseUserData.add(financialAccount);
        }
        UserStorage.getInstance().setUserData(baseUserData);
        showSelectFinancialAccountListSelector();
    }

    /**
     * Called when the card has been linked.
     * @param card The added card.
     */
    @Subscribe
    public void handleResponse(Card card) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        onFinancialAccountSelected(card.mAccountId);
    }

    /**
     * Called when the bank account has been linked.
     * @param verification Verification status.
     */
    @Subscribe
    public void handleResponse(VerificationStatusResponseVo verification) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        startActivity(IntermediateFinancialAccountListActivity.class);
    }

    /**
     * Called when session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        super.handleSessionExpiredError(error);
    }

    private void showAddFinancialAccountListSelector() {
        startActivity(AddFinancialAccountListActivity.class);
    }

    private void showSelectFinancialAccountListSelector() {
        startActivity(SelectFinancialAccountListActivity.class);
    }

    private void onFinancialAccountSelected(String selectedFinancialAccountId) {
        onFinish.returnSelectedFinancialAccount(selectedFinancialAccountId);
    }

    public SelectFundingAccountConfigurationVo getConfiguration() {
        return mConfig;
    }
}