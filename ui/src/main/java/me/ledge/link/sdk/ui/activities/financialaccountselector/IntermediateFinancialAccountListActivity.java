package me.ledge.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.financialaccountselector.IntermediateFinancialAccountListModel;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.IntermediateFinancialAccountListDelegate;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.IntermediateFinancialAccountListPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.financialaccountselector.IntermediateFinancialAccountListView;


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
