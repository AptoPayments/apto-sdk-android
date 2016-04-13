package me.ledge.link.sdk.ui.models.loanapplication;

import me.ledge.link.sdk.ui.models.Model;

/**
 * A {@link Model} for the big button on the intermediate loan application screens.
 * @author Wijnand
 */
public class BigButtonModel implements Model {

    /**
     * List of actions.
     */
    public class Action {
        public static final int RETRY_LOAN_APPLICATION = 1;
        public static final int RELOAD_LOAN_OFFERS = 2;
        public static final int UPLOAD_DOCUMENTS = 3;
        public static final int CONFIRM_LOAN = 4;
        public static final int FINISH_EXTERNAL = 5;
    }

    private boolean mVisible;
    private int mLabelResource;
    private int mAction;

    /**
     * Creates a new {@link } instance.
     * @param visible Whether the big button should be shown.
     * @param labelResource Label resource ID.
     * @param action The action to take when the button is pressed.
     */
    public BigButtonModel(boolean visible, int labelResource, int action) {
        mVisible = visible;
        mLabelResource = labelResource;
        mAction = action;
    }

    /**
     * @return Whether the big button should be shown. (Will result in the plain text buttons to be hidden.)
     */
    public boolean isVisible() {
        return mVisible;
    }

    /**
     * @return Label resource ID.
     */
    public int getLabelResource() {
        return mLabelResource;
    }

    /**
     * @return The action to take when the button is pressed.
     * @see Action
     */
    public int getAction() {
        return mAction;
    }

}
