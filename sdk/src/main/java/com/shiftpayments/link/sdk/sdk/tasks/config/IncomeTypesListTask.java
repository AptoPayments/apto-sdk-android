package com.shiftpayments.link.sdk.sdk.tasks.config;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to get the list of income types.
 * @author wijnand
 */
public class IncomeTypesListTask
        extends ShiftApiTask<Void, Void, ConfigResponseVo, UnauthorizedRequestVo> {

    /**
     * Creates a new {@link IncomeTypesListTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public IncomeTypesListTask(UnauthorizedRequestVo requestData, ShiftApiWrapper apiWrapper,
                               ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected ConfigResponseVo callApi() throws ApiException {
        ContextConfigResponseVo response = getApiWrapper().getUserConfig(getRequestData());
        return response.projectConfiguration;
    }
}
