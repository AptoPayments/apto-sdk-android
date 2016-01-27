package us.ledge.line.sdk.sdk.presenters;

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

    /** {@inheritDoc} */
    @Override
    public void attachView(V view) {
        super.attachView(view);
        mActivity.setTitle(mModel.getActivityTitleResource());
        setupToolbar();
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
}
