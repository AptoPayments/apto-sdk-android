package com.shiftpayments.link.sdk.ui.activities.card;

import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.CardSettingsDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.CardSettingsPresenter;
import com.shiftpayments.link.sdk.ui.views.card.CardSettingsView;
import com.venmo.android.pin.PinListener;


/**
 * Created by adrian on 03/08/2018.
 */

public class CardSettingsActivity extends FragmentMvpActivity implements PinListener {

    /** {@inheritDoc} */
    @Override
    protected CardSettingsView createView() {
        return (CardSettingsView) View.inflate(this, R.layout.act_card_settings, null);
    }

    /** {@inheritDoc} */
    @Override
    protected CardSettingsPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof CardSettingsDelegate) {
            return new CardSettingsPresenter(this, (CardSettingsDelegate) delegate, getSupportFragmentManager());
        }
        else {
            throw new NullPointerException("Received Module does not implement CardSettingsDelegate!");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
