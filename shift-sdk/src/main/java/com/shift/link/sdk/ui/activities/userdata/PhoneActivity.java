package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.PhoneModel;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PhoneDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PhonePresenter;
import com.shift.link.sdk.ui.views.userdata.PhoneView;

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
