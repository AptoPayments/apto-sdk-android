package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.Menu;
import android.view.View;

import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;

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
