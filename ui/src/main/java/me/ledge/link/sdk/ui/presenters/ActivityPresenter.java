package me.ledge.link.sdk.ui.presenters;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;

/**
 * Generic {@link Presenter} for an {@link AppCompatActivity}.
 * @author Wijnand
 */
public abstract class ActivityPresenter<M extends ActivityModel, V extends View & ViewWithToolbar>
        extends BasePresenter<M, V> {

    protected final AppCompatActivity mActivity;
    protected ActionBar mActionBar;

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

    /**
     * Starts another activity.
     * @param activity The Activity to start.
     */
    protected void startActivity(Class activity) {
        if (activity != null) {
            mActivity.startActivity(getStartIntent(activity));
        }

        mActivity.finish();
    }

    /**
     * @param activity The Activity to start.
     * @return The {@link Intent} to use to start the next Activity.
     */
    protected Intent getStartIntent(Class activity) {
        return new Intent(mActivity, activity);
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(V view) {
        super.attachView(view);
        mActivity.setTitle(mModel.getActivityTitleResource());
        setupToolbar();
    }

    /**
     * Starts the previous activity.
     */
    public void startPreviousActivity() {
        startActivity(mModel.getPreviousActivity(mActivity));
    }

    /**
     * Starts the next activity.
     */
    public void startNextActivity() {
        startActivity(mModel.getNextActivity(mActivity));
    }
}
