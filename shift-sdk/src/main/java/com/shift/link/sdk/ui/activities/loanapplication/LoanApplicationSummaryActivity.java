package com.shift.link.sdk.ui.activities.loanapplication;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryDelegate;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.loanapplication.LoanApplicationSummaryView;

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
