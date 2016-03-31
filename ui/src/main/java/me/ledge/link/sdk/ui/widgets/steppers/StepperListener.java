package me.ledge.link.sdk.ui.widgets.steppers;

/**
 * Callbacks the {@link DotStepperWidget} will invoke.
 * @author Wijnand
 */
public interface StepperListener {

    /**
     * Called when the "back" button is pressed.
     */
    void stepperBackClickHandler();

    /**
     * Called when the "next" button is pressed.
     */
    void stepperNextClickHandler();
}
