package us.ledge.line.sdk.sdk.activities.userdata;

import android.view.View;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.MvpActivity;
import us.ledge.line.sdk.sdk.models.userdata.LoanAmountModel;
import us.ledge.line.sdk.sdk.presenters.userdata.LoanAmountPresenter;
import us.ledge.line.sdk.sdk.views.userdata.LoanAmountView;

/**
 * Wires up the MVP pattern for the loan amount screen.
 * @author Wijnand
 */
public class LoanAmountActivity
        extends MvpActivity<LoanAmountModel, LoanAmountView, LoanAmountPresenter> {

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
