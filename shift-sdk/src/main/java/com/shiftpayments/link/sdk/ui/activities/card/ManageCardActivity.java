package com.shiftpayments.link.sdk.ui.activities.card;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.models.card.ManageCardModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.ManageCardDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.ManageCardPresenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.card.ManageCardView;


/**
 * Created by adrian on 27/11/2017.
 */

public class ManageCardActivity extends FragmentMvpActivity<ManageCardModel, ManageCardView, ManageCardPresenter> {

    private ManageCardDelegate mDelegate;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mPresenter.subscribeToEvents(true);
        mPresenter.refreshView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.subscribeToEvents(false);
    }

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
            return new ManageCardPresenter(this, mDelegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ManageCardDelegate!");
        }

    }

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        MenuItem updateProfileItem;
        /*if(CardStorage.getInstance().getCard().physicalCardActivationRequired) {*/
        //TODO: testing
        if(true) {
            inflater.inflate(R.menu.menu_update_profile_and_activate_physical_card, menu);
            updateProfileItem = menu.getItem(1);

        }
        else {
            inflater.inflate(R.menu.menu_update_profile, menu);
            updateProfileItem = menu.getItem(0);
        }
        Drawable accountIcon = getResources().getDrawable(R.drawable.ic_icon_account);
        final PorterDuffColorFilter colorFilter
                = new PorterDuffColorFilter(UIStorage.getInstance().getIconTertiaryColor(), PorterDuff.Mode.SRC_ATOP);
        accountIcon.setColorFilter(colorFilter);
        updateProfileItem.setIcon(accountIcon);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();
        if (i == R.id.menu_update_profile) {
            mPresenter.accountClickHandler();
            return true;
        } else if (i == R.id.menu_activate_card_button) {
            mPresenter.activatePhysicalCard();
            return true;
        } else if(i ==android.R.id.home) {
            mDelegate.onManageCardClosed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // Disabled
    }
}
