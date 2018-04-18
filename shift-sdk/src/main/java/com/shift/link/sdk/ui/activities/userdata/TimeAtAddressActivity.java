package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.TimeAtAddressModel;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.TimeAtAddressDelegate;
import com.shift.link.sdk.ui.presenters.userdata.TimeAtAddressPresenter;
import com.shift.link.sdk.ui.views.userdata.TimeAtAddressView;

/**
 * Wires up the MVP pattern for the time at address screen.
 * @author Adrian
 */
public class TimeAtAddressActivity extends UserDataActivity<TimeAtAddressModel, TimeAtAddressView, TimeAtAddressPresenter> {

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
