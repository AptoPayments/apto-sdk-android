package me.ledge.link.sdk.ui.activities.loanapplication;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryDelegate;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationSummaryView;

/**
 * Wires up the MVP pattern for the screen that shows the application summary.
 * @author Adrian
 */
public class LoanApplicationSummaryActivity extends MvpActivity<LoanApplicationSummaryModel, LoanApplicationSummaryView, LoanApplicationSummaryPresenter> {

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationSummaryView createView() {
        return (LoanApplicationSummaryView) View.inflate(this, R.layout.act_loan_application_summary, null);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationSummaryPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof LoanApplicationSummaryDelegate) {
            return new LoanApplicationSummaryPresenter(this, (LoanApplicationSummaryDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement LoanApplicationSummaryDelegate!");
        }
    }
}
