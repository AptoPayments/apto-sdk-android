package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.PhoneModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.PhoneDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.PhonePresenter;
import me.ledge.link.sdk.ui.views.userdata.PhoneView;

/**
 * Wires up the MVP pattern for the phone screen.
 * @author Adrian
 */
public class PhoneActivity
        extends UserDataActivity<PhoneModel, PhoneView, PhonePresenter> {

    /** {@inheritDoc} */
    @Override
    protected PhoneView createView() {
        return (PhoneView) View.inflate(this, R.layout.act_phone, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PhonePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PhoneDelegate) {
            return new PhonePresenter(this, (PhoneDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement PhoneDelegate!");
        }
    }
}
