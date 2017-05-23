package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.TimeAtAddressModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.TimeAtAddressDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.TimeAtAddressPresenter;
import me.ledge.link.sdk.ui.views.userdata.TimeAtAddressView;

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
