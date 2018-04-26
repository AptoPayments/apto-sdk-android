package com.shiftpayments.link.sdk.ui.activities.loanapplication;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.loanapplication.LoanApplicationSummaryView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.LoanApplicationSummaryPresenter;

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
