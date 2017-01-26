package me.ledge.link.sdk.ui.activities.financialaccountselector;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.financialaccountselector.SelectFinancialAccountListModel;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListDelegate;
import me.ledge.link.sdk.ui.presenters.financialaccountselector.SelectFinancialAccountListPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.financialaccountselector.SelectFinancialAccountListView;


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
