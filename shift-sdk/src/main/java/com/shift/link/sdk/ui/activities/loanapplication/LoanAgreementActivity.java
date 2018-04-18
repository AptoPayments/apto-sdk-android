package com.shift.link.sdk.ui.activities.loanapplication;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanAgreementDelegate;
import com.shift.link.sdk.ui.presenters.loanapplication.LoanAgreementPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.loanapplication.LoanAgreementView;

/**
 * Wires up the MVP pattern for the screen that shows the loan agreement.
 * @author Wijnand
 */
public class LoanAgreementActivity extends MvpActivity<LoanAgreementModel, LoanAgreementView, LoanAgreementPresenter> {

    /** {@inheritDoc} */
    @Override
    protected LoanAgreementView createView() {
        return (LoanAgreementView) View.inflate(this, R.layout.act_loan_agreement, null);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanAgreementPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof LoanAgreementDelegate) {
            return new LoanAgreementPresenter(this, (LoanAgreementDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement LoanAgreementDelegate!");
        }
    }
}