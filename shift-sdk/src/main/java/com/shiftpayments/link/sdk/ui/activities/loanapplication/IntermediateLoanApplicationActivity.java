package com.shiftpayments.link.sdk.ui.activities.loanapplication;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.IntermediateLoanApplicationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.IntermediateLoanApplicationPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.loanapplication.IntermediateLoanApplicationView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.IntermediateLoanApplicationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.IntermediateLoanApplicationPresenter;

/**
 * Wires up the MVP pattern for the screen that shows a loan in an intermediate state.
 * @author Wijnand
 */
public class IntermediateLoanApplicationActivity
        extends MvpActivity<IntermediateLoanApplicationModel, IntermediateLoanApplicationView, IntermediateLoanApplicationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected IntermediateLoanApplicationView createView() {
        return (IntermediateLoanApplicationView) View.inflate(this, R.layout.act_loan_application_intermediate, null);
    }

    /** {@inheritDoc} */
    @Override
    protected IntermediateLoanApplicationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof IntermediateLoanApplicationDelegate) {
            return new IntermediateLoanApplicationPresenter(this, (IntermediateLoanApplicationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement IntermediateLoanApplicationDelegate!");
        }
    }
}
