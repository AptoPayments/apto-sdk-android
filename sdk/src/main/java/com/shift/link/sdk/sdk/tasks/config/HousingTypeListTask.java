package com.shift.link.sdk.sdk.tasks.config;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shift.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.ContextConfigResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to get the list of housing types.
 * @author wijnand
 */
public class HousingTypeListTask
        extends ShiftApiTask<Void, Void, ConfigResponseVo, UnauthorizedRequestVo> {

    /**
     * Creates a new {@link HousingTypeListTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public HousingTypeListTask(UnauthorizedRequestVo requestData, ShiftApiWrapper apiWrapper,
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
