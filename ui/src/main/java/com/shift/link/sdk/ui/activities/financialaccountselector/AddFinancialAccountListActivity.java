package com.shift.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import com.shift.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListDelegate;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.financialaccountselector.AddFinancialAccountListView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.financialaccountselector.AddFinancialAccountListModel;
import com.shift.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListDelegate;
import com.shift.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.financialaccountselector.AddFinancialAccountListView;


/**
 * Wires up the MVP pattern for the screen that shows the financial account list.
 * @author Adrian
 */
public class AddFinancialAccountListActivity
        extends MvpActivity<AddFinancialAccountListModel, AddFinancialAccountListView, AddFinancialAccountListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddFinancialAccountListView createView() {
        return (AddFinancialAccountListView) View.inflate(this, R.layout.act_add_financial_accounts, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddFinancialAccountListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddFinancialAccountListDelegate) {
            return new AddFinancialAccountListPresenter(this, (AddFinancialAccountListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddFinancialAccountListDelegate!");
        }
    }
}
