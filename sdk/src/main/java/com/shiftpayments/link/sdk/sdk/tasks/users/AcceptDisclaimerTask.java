package com.shiftpayments.link.sdk.sdk.tasks.users;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.users.AcceptDisclaimerRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to accept a disclaimer
 * @author Adrian
 */
public class AcceptDisclaimerTask extends ShiftApiTask<Void, Void, ApiEmptyResponseVo, AcceptDisclaimerRequestVo> {

    private String mApplicationId;

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param applicationId See {@link ShiftApiTask#ShiftApiTask}.
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public AcceptDisclaimerTask(String applicationId, AcceptDisclaimerRequestVo requestData, ShiftApiWrapper apiWrapper,
                                ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mApplicationId = applicationId;
    }

    /** {@inheritDoc} */
    @Override
    protected ApiEmptyResponseVo callApi() throws ApiException {
        return getApiWrapper().acceptDisclaimer(mApplicationId, getRequestData());
    }
}
