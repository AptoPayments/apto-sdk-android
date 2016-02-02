package us.ledge.link.sdk.sdk.models;

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
     * @return The previous {@link Activity} to start.
     */
    Class getPreviousActivity();

    /**
     * @return The next {@link Activity} to start.
     */
    Class getNextActivity();
}
