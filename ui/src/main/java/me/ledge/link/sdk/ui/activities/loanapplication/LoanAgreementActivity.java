package me.ledge.link.sdk.ui.activities.loanapplication;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.loanapplication.LoanAgreementModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanAgreementPresenter;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.views.loanapplication.LoanAgreementView;

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
    protected LoanAgreementPresenter createPresenter() {
        return new LoanAgreementPresenter(this, LoanApplicationModule.getInstance(this));
    }
}
