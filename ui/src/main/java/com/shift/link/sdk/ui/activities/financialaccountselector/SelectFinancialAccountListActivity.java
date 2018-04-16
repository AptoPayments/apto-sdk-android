package com.shift.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListDelegate;
import com.shift.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.financialaccountselector.SelectFinancialAccountListView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountListModel;
import com.shift.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListDelegate;
import com.shift.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.financialaccountselector.SelectFinancialAccountListView;


/**
 * Wires up the MVP pattern for financial account list selection.
 * @author Adrian
 */
public class SelectFinancialAccountListActivity
        extends MvpActivity<SelectFinancialAccountListModel, SelectFinancialAccountListView, SelectFinancialAccountListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected SelectFinancialAccountListView createView() {
        return (SelectFinancialAccountListView) View.inflate(this, R.layout.act_select_financial_accounts, null);
    }

    /** {@inheritDoc} */
    @Override
    protected SelectFinancialAccountListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof SelectFinancialAccountListDelegate) {
            return new SelectFinancialAccountListPresenter(this, (SelectFinancialAccountListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement SelectFinancialAccountListDelegate!");
        }
    }
}
