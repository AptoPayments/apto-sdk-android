package com.shiftpayments.link.sdk.ui.geocoding.handlers;

/**
 * Callback to run when the get predictions request has been completed with error.
 *
 * @author Adrian
 */
public interface GetPredictionsOnErrorCallback {

    void execute(Exception e);

}
