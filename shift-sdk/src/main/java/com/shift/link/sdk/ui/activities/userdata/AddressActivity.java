package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.AddressModel;
import com.shift.link.sdk.ui.presenters.userdata.AddressDelegate;
import com.shift.link.sdk.ui.presenters.userdata.AddressPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.userdata.AddressView;

/**
 * Wires up the MVP pattern for the address screen.
 * @author Wijnand
 */
public class AddressActivity
        extends UserDataActivity<AddressModel, AddressView, AddressPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddressView createView() {
        return (AddressView) View.inflate(this, R.layout.act_address, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddressPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddressDelegate) {
            return new AddressPresenter(this, (AddressDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddressDelegate!");
        }
    }
}
