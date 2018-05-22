package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.TimeAtAddressModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.TimeAtAddressDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.TimeAtAddressPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.TimeAtAddressView;

/**
 * Wires up the MVP pattern for the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressActivity extends MvpActivity<TimeAtAddressModel, TimeAtAddressView, TimeAtAddressPresenter> {

    /** {@inheritDoc} */
    @Override
    protected TimeAtAddressView createView() {
        return (TimeAtAddressView) View.inflate(this, R.layout.act_time_at_address, null);
    }

    /** {@inheritDoc} */
    @Override
    protected TimeAtAddressPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof TimeAtAddressDelegate) {
            return new TimeAtAddressPresenter(this, (TimeAtAddressDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement TimeAtAddressDelegate!");
        }
    }
}
