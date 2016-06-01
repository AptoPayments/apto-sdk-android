package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.widgets.steppers.DotStepperWidget;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Generic user data related View.
 * @author Wijnand
 */
public class UserDataView<L extends StepperListener & NextButtonListener>
        extends RelativeLayout
        implements ViewWithToolbar, View.OnClickListener {

    protected L mListener;
    protected Toolbar mToolbar;
    protected TextView mNextButton;
    protected DotStepperWidget mStepper;

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
        mToolbar = (Toolbar) findViewById(R.id.tb_llsdk_toolbar);
        mNextButton = (TextView) findViewById(R.id.tv_next_bttn);
        mStepper = (DotStepperWidget) findViewById(R.id.dsw_stepper);
    }

    /**
     * Sets up all required listeners.
     */
    protected void setupListeners() {
        if (mNextButton != null) {
            mNextButton.setOnClickListener(this);
        }
        if (mStepper != null) {
            mStepper.setListener(mListener);
        }
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
     * Stores a new {@link L}istener.
     * @param listener New {@link L}istener.
     */
    public void setListener(L listener) {
        mListener = listener;

        if (mStepper != null) {
            mStepper.setListener(listener);
        }
    }

    /**
     * Stores a new stepper configuration.
     * @param configuration New configuration.
     */
    public void setStepperConfiguration(StepperConfiguration configuration) {
        if (mStepper != null) {
            mStepper.setConfiguration(configuration);
        }
    }
}
