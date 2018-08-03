package com.shiftpayments.link.sdk.ui.models;

import android.app.Activity;

/**
 * Activity-related Model.
 * @author Wijnand
 */
public interface ActivityModel extends Model {

    /**
     * @return Resource ID for the Activity title.
     */
    int getActivityTitleResource();

    /**
     * @param current The current {@link Activity}.
     * @return The previous {@link Activity} to start.
     */
    Class getPreviousActivity(Activity current);

    /**
     * @param current The current {@link Activity}.
     * @return The next {@link Activity} to start.
     */
    Class getNextActivity(Activity current);
}
