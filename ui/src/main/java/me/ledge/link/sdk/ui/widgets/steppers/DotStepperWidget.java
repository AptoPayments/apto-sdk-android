package me.ledge.link.sdk.ui.widgets.steppers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import me.ledge.link.sdk.ui.R;

/**
 * Stepper widget that indicates progress through dots.
 * @author Wijnand
 */
public class DotStepperWidget extends RelativeLayout implements View.OnClickListener {

    private LinearLayout mBackButton;
    private LinearLayout mNextButton;
    private LinearLayout mDotsHolder;
    private StepperListener mListener;

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     */
    public DotStepperWidget(Context context) {
        this(context, null);
    }

    /**
     * @see RelativeLayout#RelativeLayout
     * @param context See {@link RelativeLayout#RelativeLayout}.
     * @param attrs See {@link RelativeLayout#RelativeLayout}.
     */
    public DotStepperWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Finds all relevant child {@link View}s.
     */
    private void findAllViews() {
        mBackButton = (LinearLayout) findViewById(R.id.ll_back_button);
        mNextButton = (LinearLayout) findViewById(R.id.ll_next_button);
        mDotsHolder = (LinearLayout) findViewById(R.id.ll_dots_holder);
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

    private void updateListener(View view, boolean enabled) {
        if (enabled) {
            view.setOnClickListener(this);
        } else {
            view.setOnClickListener(null);
        }
    }

    /**
     * Creates all dots that indicate total steps and progress.
     * @param total Total steps.
     * @param position Position, current step. Zero-based.
     */
    private void generateDots(int total, int position) {
        ImageView view;

        for (int i = 0; i < total; i++) {
            view = new ImageView(getContext());

            if (i == position) {
                view.setImageDrawable(getResources().getDrawable(R.drawable.stepper_dot_enabled));
            } else {
                view.setImageDrawable(getResources().getDrawable(R.drawable.stepper_dot_disabled));
            }

            mDotsHolder.addView(view);
        }
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

        generateDots(configuration.getTotalSteps(), configuration.getPosition());
    }

    /**
     * Stores a new {@link StepperListener}.
     * @param listener New {@link StepperListener}.
     */
    public void setListener(StepperListener listener) {
        mListener = listener;
    }
}
