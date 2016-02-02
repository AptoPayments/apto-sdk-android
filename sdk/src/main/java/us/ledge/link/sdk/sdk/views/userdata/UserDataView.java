package us.ledge.link.sdk.sdk.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import us.ledge.link.sdk.sdk.R;
import us.ledge.link.sdk.sdk.views.ViewWithToolbar;

/**
 * Generic user data related View.
 * @author Wijnand
 */
public class UserDataView<L extends NextButtonListener>
        extends RelativeLayout
        implements ViewWithToolbar, View.OnClickListener {

    protected L mListener;
    protected Toolbar mToolbar;
    protected TextView mNextButton;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public UserDataView(Context context) {
        super(context);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public UserDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all references to child Views.
     */
    protected void findAllViews() {
        mToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        mNextButton = (TextView) findViewById(R.id.tv_next_bttn);
    }

    /**
     * Sets up all required listeners.
     */
    protected void setupListeners() {
        mNextButton.setOnClickListener(this);
    }

    /**
     * Updates an error display.
     * @param wrapper The {@link TextInputLayout} to update.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    protected void updateErrorDisplay(TextInputLayout wrapper, boolean show, int errorMessageId) {
        if (show) {
            wrapper.setError(getResources().getString(errorMessageId));
        } else {
            wrapper.setError(null);
            wrapper.setErrorEnabled(false);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setupListeners();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.tv_next_bttn) {
            mListener.nextClickHandler();
        }
    }

    /** {@inheritDoc} */
    @Override
    public Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * Stores a new {@link NextButtonListener}.
     * @param listener New {@link NextButtonListener}.
     */
    public void setListener(L listener) {
        mListener = listener;
    }
}
