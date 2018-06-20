package com.shiftpayments.link.sdk.sdk.tasks.cardapplication;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.CardApplicationResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to get the current application status
 * @author Adrian
 */
public class GetCardApplicationStatusTask
        extends ShiftApiTask<Void, Void, CardApplicationResponseVo, String> {

    /**
     * Creates a new {@link GetCardApplicationStatusTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public GetCardApplicationStatusTask(String requestData, ShiftApiWrapper apiWrapper,
                                        ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected CardApplicationResponseVo callApi() throws ApiException {
        return getApiWrapper().getCardApplicationStatus(getRequestData());
    }
}
