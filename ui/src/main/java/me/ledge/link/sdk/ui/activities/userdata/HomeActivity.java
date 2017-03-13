package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.HomeModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.HomeDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.HomePresenter;
import me.ledge.link.sdk.ui.views.userdata.HomeView;

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
