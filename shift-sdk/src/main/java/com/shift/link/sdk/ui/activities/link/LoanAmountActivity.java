package com.shift.link.sdk.ui.activities.link;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.userdata.UserDataActivity;
import com.shift.link.sdk.ui.models.link.LoanAmountModel;
import com.shift.link.sdk.ui.presenters.link.LoanAmountPresenter;
import com.shift.link.sdk.ui.presenters.link.LoanDataDelegate;
import com.shift.link.sdk.ui.presenters.link.LoanInfoModule;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.views.link.LoanAmountView;
import com.shift.link.sdk.ui.workflow.ModuleManager;

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

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!((LoanInfoModule) ModuleManager.getInstance().getCurrentModule()).userHasAllRequiredData) {
            return false;
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_loan_amount, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mPresenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
