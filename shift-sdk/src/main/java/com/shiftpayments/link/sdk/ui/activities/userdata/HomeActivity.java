package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.HomeModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.HomeDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.HomePresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.HomeView;

/**
 * Wires up the MVP pattern for the address validation screen.
 * @author Adrian
 */
public class HomeActivity
        extends UserDataActivity<HomeModel, HomeView, HomePresenter> {

    /** {@inheritDoc} */
    @Override
    protected HomeView createView() {
        return (HomeView) View.inflate(this, R.layout.act_home, null);
    }

    /** {@inheritDoc} */
    @Override
    protected HomePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof HomeDelegate) {
            return new HomePresenter(this, (HomeDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement HomeDelegate!");
        }
    }
}
