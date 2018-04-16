package com.shift.link.sdk.ui.presenters;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.views.ViewWithToolbar;

/**
 * Generic {@link Presenter} for an {@link AppCompatActivity}.
 * @author Wijnand
 */
public abstract class ActivityPresenter<M extends ActivityModel, V extends View & ViewWithToolbar>
        extends BasePresenter<M, V> {

    protected final AppCompatActivity mActivity;
    private ActionBar mActionBar;

    /**
     * Creates a new {@link ActivityPresenter} instance.
     * @param activity Activity.
     */
    public ActivityPresenter(AppCompatActivity activity) {
        mActivity = activity;
    }

    /**
     * Sets up the toolbar.
     */
    protected void setupToolbar() {
        initToolbar();
        showToolbarUpArrow();
    }

    /**
     * Initializes the Toolbar.
     */
    protected void initToolbar() {
        mActivity.setSupportActionBar(mView.getToolbar());
        mActionBar = mActivity.getSupportActionBar();
    }

    /**
     * Shows the "up" arrow on the Toolbar.
     */
    protected void showToolbarUpArrow() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(V view) {
        super.attachView(view);
        mActivity.setTitle(mModel.getActivityTitleResource());
        setupToolbar();
    }

    public abstract void onBack();
}
