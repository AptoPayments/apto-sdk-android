package com.shiftpayments.link.sdk.ui.activities.card;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private ManageCardDelegate mDelegate;

    /** {@inheritDoc} */
    @Override
    protected ManageCardView createView() {
        return (ManageCardView) View.inflate(this, R.layout.act_manage_card, null);
    }

    /** {@inheritDoc} */
    @Override
    protected ManageCardPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof ManageCardDelegate) {
            mDelegate = (ManageCardDelegate) delegate;
            return new ManageCardPresenter(getSupportFragmentManager(), this, mDelegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ManageCardDelegate!");
        }

    }

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_update_profile, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();
        if (i == R.id.menu_update_profile) {
            ((ManageCardPresenter) mPresenter).accountClickHandler();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {
        mDelegate.onManageCardBackPressed();
        super.onBackPressed();
    }
}
