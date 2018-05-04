package com.shiftpayments.link.sdk.sdk.tasks.config;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LinkConfigResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to display loan purposes.
 * @author wijnand
 */
public class LinkConfigTask extends ShiftApiTask<Void, Void, LinkConfigResponseVo, UnauthorizedRequestVo> {

    /**
     * Creates a new {@link LinkConfigTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public LinkConfigTask(UnauthorizedRequestVo requestData, ShiftApiWrapper apiWrapper,
                          ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LinkConfigResponseVo callApi() throws ApiException {
        return getApiWrapper().getLinkConfig(getRequestData());
    }
}
