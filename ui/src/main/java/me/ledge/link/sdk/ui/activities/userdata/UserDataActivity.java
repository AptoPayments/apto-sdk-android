package me.ledge.link.sdk.ui.activities.userdata;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.storages.UIStorage;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(UIStorage.getInstance().getStatusBarColor());
        }
    }
}
