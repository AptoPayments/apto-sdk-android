package com.shiftpayments.link.sdk.sdk.tasks.verifications;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;

/**
 * A concrete {@link ShiftApiTask} to complete user's phone verification.
 * @author Adrian
 */
public class RestartVerificationTask extends ShiftApiTask<Void, Void, VerificationResponseVo, String> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public RestartVerificationTask(String requestData, ShiftApiWrapper apiWrapper,
                                         ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected VerificationResponseVo callApi() throws ApiException {
        return getApiWrapper().restartVerification(getRequestData());
    }
}
