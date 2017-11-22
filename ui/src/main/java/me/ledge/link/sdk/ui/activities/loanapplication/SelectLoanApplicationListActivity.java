package me.ledge.link.sdk.ui.activities.loanapplication;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.loanapplication.SelectLoanApplicationListModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListDelegate;
import me.ledge.link.sdk.ui.presenters.loanapplication.SelectLoanApplicationListPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.loanapplication.SelectPendingApplicationListView;


/**
 * Wires up the MVP pattern for financial account list selection.
 * @author Adrian
 */
public class SelectLoanApplicationListActivity
        extends MvpActivity<SelectLoanApplicationListModel, SelectPendingApplicationListView, SelectLoanApplicationListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected SelectPendingApplicationListView createView() {
        return (SelectPendingApplicationListView) View.inflate(this, R.layout.act_select_pending_application, null);
    }

    /** {@inheritDoc} */
    @Override
    protected SelectLoanApplicationListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof SelectLoanApplicationListDelegate) {
            return new SelectLoanApplicationListPresenter(this, (SelectLoanApplicationListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement SelectLoanApplicationListDelegate!");
        }
    }
}
