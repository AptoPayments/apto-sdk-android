package com.shift.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.financialaccountselector.IntermediateFinancialAccountListModel;
import com.shift.link.sdk.ui.presenters.financialaccountselector.IntermediateFinancialAccountListDelegate;
import com.shift.link.sdk.ui.presenters.financialaccountselector.IntermediateFinancialAccountListPresenter;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.views.financialaccountselector.IntermediateFinancialAccountListView;


/**
 * Intermediate Activity that shows the loading view while checking if the user has financial accounts.
 * @author Adrian
 */
public class IntermediateFinancialAccountListActivity
        extends MvpActivity<IntermediateFinancialAccountListModel, IntermediateFinancialAccountListView, IntermediateFinancialAccountListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected IntermediateFinancialAccountListView createView() {
        return (IntermediateFinancialAccountListView) View.inflate(this, R.layout.act_intermediate_financial_accounts_list, null);
    }

    /** {@inheritDoc} */
    @Override
    protected IntermediateFinancialAccountListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof IntermediateFinancialAccountListDelegate) {
            return new IntermediateFinancialAccountListPresenter(this, (IntermediateFinancialAccountListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement IntermediateFinancialAccountListDelegate!");
        }
    }
}
