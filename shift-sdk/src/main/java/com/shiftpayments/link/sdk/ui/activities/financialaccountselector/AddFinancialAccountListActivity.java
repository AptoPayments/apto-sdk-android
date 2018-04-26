package com.shiftpayments.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.financialaccountselector.AddFinancialAccountListModel;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.financialaccountselector.AddFinancialAccountListView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListDelegate;
import com.shiftpayments.link.sdk.ui.presenters.financialaccountselector.AddFinancialAccountListPresenter;


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
