package me.ledge.link.sdk.ui.widgets.steppers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.utils.ResourceUtil;

/**
 * Stepper widget that indicates progress through dots.
 * @author Wijnand
 */
public class DotStepperWidget extends RelativeLayout implements View.OnClickListener {

    private LinearLayout mBackButton;
    private LinearLayout mNextButton;
    private LinearLayout mDotsHolder;
    private StepperListener mListener;

    private int mDotEnabledResourceId;

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
        init();
    }

    /**
     * Initializes this class.
     */
    private void init() {
        ResourceUtil util = new ResourceUtil();
        mDotEnabledResourceId = util.getResourceIdForAttribute(getContext(), R.attr.llsdk_stepper_dotEnabled);
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
     * Creates all dots that indicate total steps and progress.
     * @param total Total steps.
     * @param position Position, current step. Zero-based.
     */
    private void generateDots(int total, int position) {
        mDotsHolder.removeAllViews();
        ImageView view;

        for (int i = 0; i < total; i++) {
            view = new ImageView(getContext());
            view.setImageDrawable(getResources().getDrawable(mDotEnabledResourceId));

            if (i == position) {
                view.setColorFilter(UIStorage.getInstance().getPrimaryColor());
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
