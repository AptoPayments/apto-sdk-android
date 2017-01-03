package me.ledge.link.sdk.ui;

/**
 * Contains the navigation logic for each screen
 * Created by adrian on 29/12/2016.
 */

public interface Router {

    /**
     * Starts another activity.
     * @param activity The Activity to start.
     */
    void startActivity(Class activity);

}
