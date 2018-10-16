package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.PhoneModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PhoneDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PhonePresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.PhoneView;

import java.util.ArrayList;

import static com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule.EXTRA_ALLOWED_COUNTRIES;

/**
 * Wires up the MVP pattern for the phone screen.
 * @author Adrian
 */
public class PhoneActivity
        extends MvpActivity<PhoneModel, PhoneView, PhonePresenter> {

    /** {@inheritDoc} */
    @Override
    protected PhoneView createView() {
        return (PhoneView) View.inflate(this, R.layout.act_phone, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PhonePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PhoneDelegate) {
            ArrayList<String> allowedCountries = getIntent().getStringArrayListExtra(EXTRA_ALLOWED_COUNTRIES);
            return new PhonePresenter(this, (PhoneDelegate) delegate, allowedCountries);
        }
        else {
            throw new NullPointerException("Received Module does not implement PhoneDelegate!");
        }
    }
}
