package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.AddressModel;
import com.shiftpayments.link.sdk.ui.presenters.userdata.AddressDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.AddressPresenter;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.views.userdata.AddressView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.AddressDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.AddressPresenter;

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
