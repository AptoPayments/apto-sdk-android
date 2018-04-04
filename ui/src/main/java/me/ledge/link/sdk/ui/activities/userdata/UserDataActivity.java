package me.ledge.link.sdk.ui.activities.userdata;

import android.view.Menu;
import android.view.View;

import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Generic user data input related {@link MvpActivity}.
 * @deprecated No longer really needed.
 * @author Wijnand
 */
public abstract class UserDataActivity<M extends ActivityModel, V extends View & ViewWithToolbar, P extends ActivityPresenter<M, V>>
        extends MvpActivity<M, V, P> {

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
