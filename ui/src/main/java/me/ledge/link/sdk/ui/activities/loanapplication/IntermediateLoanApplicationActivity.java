package me.ledge.link.sdk.ui.activities.loanapplication;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.loanapplication.IntermediateLoanApplicationModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.IntermediateLoanApplicationPresenter;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.views.loanapplication.IntermediateLoanApplicationView;

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
    protected IntermediateLoanApplicationPresenter createPresenter() {
        return new IntermediateLoanApplicationPresenter(this, LoanApplicationModule.getInstance(this));
    }
}
