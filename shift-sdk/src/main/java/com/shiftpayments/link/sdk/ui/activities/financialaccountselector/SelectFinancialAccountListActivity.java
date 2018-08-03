package com.shiftpayments.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountListModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListPresenter;
import com.shiftpayments.link.sdk.ui.views.financialaccountselector.SelectFinancialAccountListView;


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
