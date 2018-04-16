package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.HomeDelegate;
import com.shift.link.sdk.ui.presenters.userdata.HomePresenter;
import com.shift.link.sdk.ui.views.userdata.HomeView;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.HomeModel;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.HomeDelegate;
import com.shift.link.sdk.ui.presenters.userdata.HomePresenter;
import com.shift.link.sdk.ui.views.userdata.HomeView;

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
