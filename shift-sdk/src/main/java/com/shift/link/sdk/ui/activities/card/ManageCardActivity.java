package com.shift.link.sdk.ui.activities.card;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.FragmentMvpActivity;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.presenters.card.ManageCardPresenter;
import com.shift.link.sdk.ui.views.card.ManageCardView;
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
        return new ManageCardPresenter(getSupportFragmentManager(), this);
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
