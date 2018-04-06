package me.ledge.link.sdk.ui.views.card;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import me.ledge.link.sdk.ui.R;

/**
 * Displays the manage account screen.
 * @author Adrian
 */
public class ManageAccountView
        extends CoordinatorLayout {

    private ViewListener mListener;
    private ProgressBar mSpinner;

    public ManageAccountView(Context context) {
        this(context, null);
    }

    public ManageAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener {

    }
    public void setViewListener(ViewListener viewListener) {
        mListener = viewListener;
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();
    }

    protected void findAllViews() {
        mSpinner = (ProgressBar) findViewById(R.id.pb_progress);
    }

    private void setUpListeners() {

    }

    public void showLoading(boolean show) {
        if(show) {
            mSpinner.setVisibility(View.VISIBLE);
        }
        else {
            mSpinner.setVisibility(View.GONE);
        }
    }
}
