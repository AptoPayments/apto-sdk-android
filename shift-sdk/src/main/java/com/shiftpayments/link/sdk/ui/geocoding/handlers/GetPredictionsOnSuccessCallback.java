package com.shiftpayments.link.sdk.ui.geocoding.handlers;

import com.shiftpayments.link.sdk.ui.geocoding.vos.AutocompleteResponseVo;

/**
 * Callback to run when get places predictions request has been completed successfully.
 *
 * @author Adrian
 */
public interface GetPredictionsOnSuccessCallback {

    void execute(AutocompleteResponseVo result);
}
