package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.AddressModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.AddressDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.AddressPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.AddressView;

import java.util.ArrayList;

import static com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule.EXTRA_ALLOWED_COUNTRIES;

/**
 * Wires up the MVP pattern for the address validation screen.
 * @author Adrian
 */
public class AddressActivity
        extends MvpActivity<AddressModel, AddressView, AddressPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddressView createView() {
        return (AddressView) View.inflate(this, R.layout.act_address, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddressPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddressDelegate) {
            ArrayList<String> allowedCountries = getIntent().getStringArrayListExtra(EXTRA_ALLOWED_COUNTRIES);
            return new AddressPresenter(this, (AddressDelegate) delegate, allowedCountries);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddressDelegate!");
        }
    }
}
