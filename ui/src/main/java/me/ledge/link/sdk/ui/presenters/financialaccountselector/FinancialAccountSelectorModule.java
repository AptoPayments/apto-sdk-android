package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.app.Activity;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.api.vos.datapoints.VirtualCard;
import me.ledge.link.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.activities.financialaccountselector.AddBankAccountActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.AddCardActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.AddFinancialAccountListActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.IntermediateFinancialAccountListActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.SelectFinancialAccountListActivity;
import me.ledge.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountModel;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class FinancialAccountSelectorModule extends LedgeBaseModule
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
    public void virtualCardIssued(VirtualCard virtualCard) {
        onFinancialAccountSelected(virtualCard);
    }

    @Override
    public void cardAdded(Card card) {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.addCard(card);
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
        AddBankAccountRequestVo request = new AddBankAccountRequestVo();
        request.publicToken = token;
        LedgeLinkUi.addBankAccount(request);
        startActivity(IntermediateFinancialAccountListActivity.class);
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
        onFinancialAccountSelected(model.getFinancialAccount());
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

    @Subscribe
    public void handleResponse(Card card) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        onFinancialAccountSelected(card);
    }


    private void showAddFinancialAccountListSelector() {
        startActivity(AddFinancialAccountListActivity.class);
    }

    private void showSelectFinancialAccountListSelector() {
        startActivity(SelectFinancialAccountListActivity.class);
    }

    private void onFinancialAccountSelected(FinancialAccountVo selectedFinancialAccount) {
        onFinish.returnSelectedFinancialAccount(selectedFinancialAccount);
    }

    public SelectFundingAccountConfigurationVo getConfiguration() {
        return mConfig;
    }
}