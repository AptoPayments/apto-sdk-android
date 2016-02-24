package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.userdata.LoanAmountModel;
import me.ledge.link.sdk.ui.presenters.userdata.LoanAmountPresenter;
import me.ledge.link.sdk.ui.views.userdata.LoanAmountView;

/**
 * Wires up the MVP pattern for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountActivity
        extends UserDataActivity<LoanAmountModel, LoanAmountView, LoanAmountPresenter> {

    /** {@inheritDoc} */
    @Override
    protected LoanAmountView createView() {
        return (LoanAmountView) View.inflate(this, R.layout.act_loan_amount, null);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanAmountPresenter createPresenter() {
        return new LoanAmountPresenter(this);
    }
}
