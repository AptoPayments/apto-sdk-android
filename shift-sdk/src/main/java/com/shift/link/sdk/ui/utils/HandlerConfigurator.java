package com.shift.link.sdk.ui.utils;

import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shift.link.sdk.ui.activities.MvpActivity;

import java.util.ArrayList;

/**
 * Provide configuration information for a {@link ApiResponseHandler} and {@link MvpActivity} process order combination.
 * @author Wijnand
 */
public interface HandlerConfigurator {

    /**
     * @return User data collection process order.
     */
    ArrayList<Class<? extends MvpActivity>> getProcessOrder();

    /**
     * @return Concrete API response handler.
     */
    ApiResponseHandler getResponseHandler();
}