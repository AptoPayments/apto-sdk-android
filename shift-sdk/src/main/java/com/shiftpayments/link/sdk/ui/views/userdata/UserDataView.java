package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.DisplayErrorMessage;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.ProgressBarWidget;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperConfiguration;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Generic user data related View.
 * @author Wijnand
 */
public class UserDataView<L extends StepperListener & NextButtonListener>
        extends RelativeLayout
        implements DisplayErrorMessage, ViewWithToolbar, View.OnClickListener {

    protected L mListener;
    protected Toolbar mToolbar;
    protected TextView mNextButton;
    protected ProgressBarWidget mProgresBar;

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
        mToolbar = findViewById(R.id.tb_llsdk_toolbar);
        mNextButton = findViewById(R.id.tv_next_bttn);
        mProgresBar = findViewById(R.id.pbw_progress_bar_wrapper);
    }

    /**
     * Sets up all required listeners.
     */
    protected void setupListeners() {
        if (mNextButton != null) {
            mNextButton.setOnClickListener(this);
        }
        if (mProgresBar != null) {
            mProgresBar.setListener(mListener);
        }
    }

    protected void setColors() {
        int color = UIStorage.getInstance().getPrimaryColor();
        int contrastColor = UIStorage.getInstance().getPrimaryContrastColor();
        if (mNextButton != null) {
            mNextButton.setBackgroundColor(color);
            mNextButton.setTextColor(contrastColor);
        }
        mToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.llsdk_actionbar_background)));
        if(mProgresBar != null) {
            mProgresBar.setProgressBarColor(color);
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
        setColors();
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

    @Override
    public void displayErrorMessage(String message) {
        if(!message.isEmpty()) {
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Stores a new {@link L}istener.
     * @param listener New {@link L}istener.
     */
    public void setListener(L listener) {
        mListener = listener;

        if (mProgresBar != null) {
            mProgresBar.setListener(listener);
        }
    }

    /**
     * Stores a new stepper configuration.
     * @param configuration New configuration.
     */
    public void setStepperConfiguration(StepperConfiguration configuration) {
        if (mProgresBar != null) {
            mProgresBar.setConfiguration(configuration);
        }
    }
}
