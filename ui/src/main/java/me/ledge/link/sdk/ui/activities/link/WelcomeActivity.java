package me.ledge.link.sdk.ui.activities.link;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.UserDataActivity;
import me.ledge.link.sdk.ui.models.link.WelcomeModel;
import me.ledge.link.sdk.ui.presenters.link.WelcomeDelegate;
import me.ledge.link.sdk.ui.presenters.link.WelcomePresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.link.WelcomeView;

/**
 * Wires up the MVP pattern for the welcome screen.
 * @author Adrian
 */
public class WelcomeActivity extends UserDataActivity<WelcomeModel, WelcomeView, WelcomePresenter> {

    /** {@inheritDoc} */
    @Override
    protected WelcomeView createView() {
        return (WelcomeView) View.inflate(this, R.layout.act_welcome, null);
    }

    /** {@inheritDoc} */
    @Override
    protected WelcomePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof WelcomeDelegate) {
            return new WelcomePresenter(this, (WelcomeDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement WelcomeDelegate!");
        }
    }
}
