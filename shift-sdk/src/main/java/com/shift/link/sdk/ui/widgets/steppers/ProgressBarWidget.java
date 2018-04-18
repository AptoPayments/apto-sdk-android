package com.shift.link.sdk.ui.widgets.steppers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.shift.link.sdk.ui.R;

/**
 * Stepper widget that indicates progress through a progress bar.
 * @author Adrian
 */
public class ProgressBarWidget extends RelativeLayout implements View.OnClickListener {

    private LinearLayout mBackButton;
    private LinearLayout mNextButton;
    private ProgressBar mProgressBar;
    private StepperListener mListener;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public ProgressBarWidget(Context context) {
        this(context, null);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public ProgressBarWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }

    /**
     * Finds all relevant child {@link View}s.
     */
    private void findAllViews() {
        mBackButton = (LinearLayout) findViewById(R.id.ll_back_button);
        mNextButton = (LinearLayout) findViewById(R.id.ll_next_button);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress_bar);
    }

    /**
     * Updates a {@link View}'s functionality.
     * @param view The View to update.
     * @param enabled Whether the View should be enabled.
     */
    private void updateFunctionality(View view, boolean enabled) {
        if (enabled) {
            view.setAlpha(1);
        } else {
            view.setAlpha(0.5f);
        }
    }

    /**
     * Updates the {@link #onClick(View)} listener for a {@link View}.
     * @param view The View to update.
     * @param enabled Whether the listener should be enabled.
     */
    private void updateListener(View view, boolean enabled) {
        if (enabled) {
            view.setOnClickListener(this);
        } else {
            view.setOnClickListener(null);
        }
    }

    /**
     * Updates the progress of the bar.
     * @param total Total steps.
     * @param position Position, current step. Zero-based.
     */
    private void updateProgressBar(int total, int position) {
        int progress = (int) (((position+1) / (float) total)*100);
        mProgressBar.setProgress(progress);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.ll_back_button) {
            mListener.stepperBackClickHandler();
        } else if (id == R.id.ll_next_button) {
            mListener.stepperNextClickHandler();
        }
    }

    /**
     * Updates the stepper configuration.
     * @param configuration The new configuration.
     */
    public void setConfiguration(StepperConfiguration configuration) {
        updateFunctionality(mBackButton, configuration.isBackEnabled());
        updateFunctionality(mNextButton, configuration.isNextEnabled());

        updateListener(mBackButton, configuration.isBackEnabled());
        updateListener(mNextButton, configuration.isNextEnabled());

        updateProgressBar(configuration.getTotalSteps(), configuration.getPosition());
    }

    /**
     * Stores a new {@link StepperListener}.
     * @param listener New {@link StepperListener}.
     */
    public void setListener(StepperListener listener) {
        mListener = listener;
    }

    public void setProgressBarColor(int color) {
        mProgressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mProgressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
