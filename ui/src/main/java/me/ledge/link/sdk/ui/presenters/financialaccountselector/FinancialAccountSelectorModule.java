package me.ledge.link.sdk.ui.presenters.financialaccountselector;

import android.app.Activity;
import android.util.Log;

import me.ledge.link.api.vos.Card;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.requests.financialaccounts.AddBankAccountRequestVo;
import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.activities.financialaccountselector.AddBankAccountActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.AddCardActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.AddFinancialAccountListActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.IntermediateFinancialAccountListActivity;
import me.ledge.link.sdk.ui.activities.financialaccountselector.SelectFinancialAccountListActivity;
import me.ledge.link.sdk.ui.storages.UserStorage;

/**
 * Created by adrian on 29/12/2016.
 */

public class FinancialAccountSelectorModule extends LedgeBaseModule
        implements AddFinancialAccountListDelegate, AddCardDelegate, AddBankAccountDelegate,
        SelectFinancialAccountListDelegate, IntermediateFinancialAccountListDelegate {

    private static FinancialAccountSelectorModule instance;
    public Command onBack;

    public static synchronized FinancialAccountSelectorModule getInstance(Activity activity) {
        if (instance == null) {
            instance = new FinancialAccountSelectorModule(activity);
        }
        return instance;
    }

    private FinancialAccountSelectorModule(Activity activity) {
        super(activity);
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
    public void virtualCardIssued() {
        Log.d("ADRIAN", "virtual card issued correctly");
        //startActivity(AutoPayActivity.class);
    }

    @Override
    public void cardAdded(Card card) {
        //Send card token to the ledge platform
        Log.d("ADRIAN", "card received: " + card.toJSON().toString());
        LedgeLinkUi.addCard(card);
        //startActivity(AutoPayActivity.class);
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
        //Send card token to the ledge platform
        Log.d("ADRIAN", "token received: " + token);
        AddBankAccountRequestVo request = new AddBankAccountRequestVo();
        request.publicToken = token;
        LedgeLinkUi.addBankAccount(request);
        Log.d("ADRIAN", "request sent!");
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
    public void onIntermediateFinancialAccountListBackPressed() {
        onBack.execute();
    }

    @Override
    public void noFinancialAccountsReceived() {
        showAddFinancialAccountListSelector();
    }

    @Override
    public void financialAccountsReceived(DataPointList userData) {
        UserStorage.getInstance().setUserData(userData);
        showSelectFinancialAccountListSelector();
    }

    private void showAddFinancialAccountListSelector() {
        startActivity(AddFinancialAccountListActivity.class);
    }

    private void showSelectFinancialAccountListSelector() {
        startActivity(SelectFinancialAccountListActivity.class);
    }
}