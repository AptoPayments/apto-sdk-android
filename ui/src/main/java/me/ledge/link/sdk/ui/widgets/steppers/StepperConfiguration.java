package me.ledge.link.sdk.ui.widgets.steppers;

/**
 * {@link DotStepperWidget} configuration.
 * @author Wijnand
 */
public class StepperConfiguration {

    private int mTotalSteps;
    private int mPosition;
    private boolean mBackEnabled;
    private boolean mNextEnabled;

    /**
     * Creates a new {@link StepperConfiguration} instance.
     * @param totalSteps Total steps.
     * @param position Position.
     * @param backEnabled Whether the "back" button should be enabled.
     * @param nextEnabled Whether the "next" button should be enabled.
     */
    public StepperConfiguration(int totalSteps, int position, boolean backEnabled, boolean nextEnabled) {
        mTotalSteps = totalSteps;
        mPosition = position;
        mBackEnabled = backEnabled;
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
     * @return Whether the "back" button should be enabled.
     */
    public boolean isBackEnabled() {
        return mBackEnabled;
    }

    /**
     * @return Whether the "next" button should be enabled.
     */
    public boolean isNextEnabled() {
        return mNextEnabled;
    }
}
