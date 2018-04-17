package com.shift.link.sdk.ui.activities.fundingaccountselector;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.fundingaccountselector.DisplayCardModel;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.DisplayCardDelegate;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.DisplayCardPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.fundingaccountselector.DisplayCardView;

/**
 * Wires up the MVP pattern for the display card screen.
 * @author Adrian
 */

public class DisplayCardActivity
        extends MvpActivity<DisplayCardModel, DisplayCardView, DisplayCardPresenter> {

    /** {@inheritDoc} */
    @Override
    protected DisplayCardView createView() {
        return (DisplayCardView) View.inflate(this, R.layout.act_display_card, null);
    }

    /** {@inheritDoc} */
    @Override
    protected DisplayCardPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof DisplayCardDelegate) {
            return new DisplayCardPresenter(this, (DisplayCardDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement DisplayCardDelegate!");
        }
    }
}
