package us.ledge.line.sdk.sdk.presenters;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import us.ledge.line.sdk.sdk.models.ActivityModel;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;

/**
 * Generic {@link Presenter} for an {@link AppCompatActivity}.
 * @author Wijnand
 */
public abstract class ActivityPresenter<M extends ActivityModel, V extends View & ViewWithToolbar>
        extends BasePresenter<M, V> {

    protected final AppCompatActivity mActivity;

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
        mActivity.setSupportActionBar(mView.getToolbar());

        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
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
        startActivity(mModel.getPreviousActivity());
    }

    /**
     * Starts the next activity.
     */
    public void startNextActivity() {
        startActivity(mModel.getNextActivity());
    }
}
