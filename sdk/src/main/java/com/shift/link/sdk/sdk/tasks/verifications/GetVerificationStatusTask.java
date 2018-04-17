package com.shift.link.sdk.sdk.tasks.verifications;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to complete user's phone verification.
 * @author Adrian
 */
public class GetVerificationStatusTask extends ShiftApiTask<Void, Void, VerificationStatusResponseVo, String> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public GetVerificationStatusTask(String requestData, ShiftApiWrapper apiWrapper,
                                     ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected VerificationStatusResponseVo callApi() throws ApiException {
        return getApiWrapper().getVerificationStatus(getRequestData());
    }
}
