package com.shiftpayments.link.sdk.ui.activities.link;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.link.LoanAmountModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.link.LoanAmountPresenter;
import com.shiftpayments.link.sdk.ui.presenters.link.LoanDataDelegate;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.views.link.LoanAmountView;

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
    protected LoanAmountPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof LoanDataDelegate) {
            return new LoanAmountPresenter(this, (LoanDataDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement LoanDataDelegate!");
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(UserStorage.getInstance().hasBearerToken()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_update_profile, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mPresenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
