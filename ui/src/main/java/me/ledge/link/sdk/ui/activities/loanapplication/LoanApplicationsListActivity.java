package me.ledge.link.sdk.ui.activities.loanapplication;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationsListModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationsListPresenter;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationsListView;

/**
 * Wires up the MVP pattern for the screen that shows the open loan applications list.
 * @author Wijnand
 */
public class LoanApplicationsListActivity
        extends MvpActivity<LoanApplicationsListModel, LoanApplicationsListView, LoanApplicationsListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationsListView createView() {
        return (LoanApplicationsListView) View.inflate(this, R.layout.act_loan_applications_list, null);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationsListPresenter createPresenter() {
        return new LoanApplicationsListPresenter(this);
    }
}
