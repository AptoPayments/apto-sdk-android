package com.shiftpayments.link.sdk.sdk.tasks.verifications;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to complete user's phone verification.
 * @author Adrian
 */
public class CompleteVerificationTask extends ShiftApiTask<Void, Void, FinishVerificationResponseVo, VerificationRequestVo> {

    private String mVerificationId;
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public CompleteVerificationTask(VerificationRequestVo requestData, String verificationId, ShiftApiWrapper apiWrapper,
                                    ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mVerificationId = verificationId;
    }

    /** {@inheritDoc} */
    @Override
    protected FinishVerificationResponseVo callApi() throws ApiException {
        return getApiWrapper().completeVerification(getRequestData(), mVerificationId);
    }
}
