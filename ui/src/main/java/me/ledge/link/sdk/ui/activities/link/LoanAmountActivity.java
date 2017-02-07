package me.ledge.link.sdk.ui.activities.link;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.UserDataActivity;
import me.ledge.link.sdk.ui.models.link.LoanAmountModel;
import me.ledge.link.sdk.ui.presenters.link.LoanAmountPresenter;
import me.ledge.link.sdk.ui.presenters.link.LoanDataDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
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
    protected LoanAmountPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof LoanDataDelegate) {
            return new LoanAmountPresenter(this, (LoanDataDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement LoanDataDelegate!");
        }
    }
}