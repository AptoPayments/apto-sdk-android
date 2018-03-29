package me.ledge.link.sdk.ui.activities.card;

import android.view.View;

import com.venmo.android.pin.PinListener;

import me.ledge.link.sdk.ui.R;

import me.ledge.link.sdk.ui.activities.FragmentMvpActivity;
import me.ledge.link.sdk.ui.presenters.card.ManageCardPresenter;
import me.ledge.link.sdk.ui.views.card.ManageCardView;


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
    protected ManageCardPresenter createPresenter() {
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
