package com.shiftpayments.link.sdk.sdk.tasks.config;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardconfig.CardConfigResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to get the card config.
 * @author Adrian
 */
public class CardConfigTask extends ShiftApiTask<Void, Void, CardConfigResponseVo, UnauthorizedRequestVo> {

    /**
     * Creates a new {@link CardConfigTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public CardConfigTask(UnauthorizedRequestVo requestData, ShiftApiWrapper apiWrapper,
                          ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected CardConfigResponseVo callApi() throws ApiException {
        return getApiWrapper().getCardConfig(getRequestData());
    }
}
