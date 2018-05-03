package com.shiftpayments.link.sdk.ui.activities.card;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.ManageCardDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.ManageCardPresenter;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardView;
import com.venmo.android.pin.PinListener;


/**
 * Created by adrian on 27/11/2017.
 */

public class ManageCardActivity extends FragmentMvpActivity implements PinListener {
    /** {@inheritDoc} */
    @Override
    protected ManageCardView createView() {
        return (ManageCardView) View.inflate(this, R.layout.act_manage_card, null);
    }

    /** {@inheritDoc} */
    @Override
    protected ManageCardPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof ManageCardDelegate) {
            return new ManageCardPresenter(getSupportFragmentManager(), this, (ManageCardDelegate)delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ManageCardDelegate!");
        }

    }

    @Override
    public void onValidated() {
        // Do nothing
    }

    @Override
    public void onPinCreated() {
        // Do nothing
    }
}
