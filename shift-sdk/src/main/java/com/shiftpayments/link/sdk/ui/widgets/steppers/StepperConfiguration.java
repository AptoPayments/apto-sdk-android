package com.shiftpayments.link.sdk.ui.widgets.steppers;

/**
 * {@link ProgressBarWidget} configuration.
 * @author Wijnand
 */
public class StepperConfiguration {

    private int mTotalSteps;
    private int mPosition;
    private boolean mNextEnabled;

    /**
     * Creates a new {@link StepperConfiguration} instance.
     * @param totalSteps Total steps.
     * @param position Position.
     * @param nextEnabled Whether the "next" button should be enabled.
     */
    public StepperConfiguration(int totalSteps, int position, boolean nextEnabled) {
        mTotalSteps = totalSteps;
        mPosition = position;
        mNextEnabled = nextEnabled;
    }

    /**
     * @return Total steps.
     */
    public int getTotalSteps() {
        return mTotalSteps;
    }

    /**
     * @return Position.
     */
    public int getPosition() {
        return mPosition;
    }

    /**
     * @return Whether the "next" button should be enabled.
     */
    public boolean isNextEnabled() {
        return mNextEnabled;
    }
}
