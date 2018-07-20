package com.shiftpayments.link.sdk.api.vos.requests.verifications;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.verifications.CompleteVerificationTask;

/**
 * Request data to complete the user's verification.
 * @author Adrian
 */
public class VerificationRequestVo extends UnauthorizedRequestVo {
    /**
     * Secret input from user
     */
    public String secret;

    public String verificationId;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new CompleteVerificationTask(this, verificationId, shiftApiWrapper, responseHandler);
    }
}
