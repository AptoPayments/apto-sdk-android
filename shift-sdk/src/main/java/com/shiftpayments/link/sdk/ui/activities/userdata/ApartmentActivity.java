package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.ApartmentModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.ApartmentDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.ApartmentPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.ApartmentView;

/**
 * Wires up the MVP pattern for the apartment screen.
 * @author Wijnand
 */
public class ApartmentActivity
        extends MvpActivity<ApartmentModel, ApartmentView, ApartmentPresenter> {

    /** {@inheritDoc} */
    @Override
    protected ApartmentView createView() {
        return (ApartmentView) View.inflate(this, R.layout.act_apartment, null);
    }

    /** {@inheritDoc} */
    @Override
    protected ApartmentPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof ApartmentDelegate) {
            return new ApartmentPresenter(this, (ApartmentDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ApartmentDelegate!");
        }
    }
}
