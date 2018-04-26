package com.shiftpayments.link.sdk.ui.workflow;

/**
 * Contains the navigation logic for each screen
 * Created by adrian on 29/12/2016.
 */

public interface NavigationCommand {

    /**
     * Starts another activity.
     * @param activity The Activity to start.
     */
    void startActivity(Class activity);

}
