package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PaydayLoanDelegate;
import com.shift.link.sdk.ui.views.userdata.PaydayLoanView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.PaydayLoanModel;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PaydayLoanDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PaydayLoanPresenter;
import com.shift.link.sdk.ui.views.userdata.PaydayLoanView;

/**
 * Wires up the MVP pattern for the payday loan screen.
 * @author Adrian
 */
public class PaydayLoanActivity extends UserDataActivity<PaydayLoanModel, PaydayLoanView, PaydayLoanPresenter> {

    /** {@inheritDoc} */
    @Override
    protected PaydayLoanView createView() {
        return (PaydayLoanView) View.inflate(this, R.layout.act_payday_loan, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PaydayLoanPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PaydayLoanDelegate) {
            return new PaydayLoanPresenter(this, (PaydayLoanDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement PaydayLoanDelegate!");
        }
    }
}
