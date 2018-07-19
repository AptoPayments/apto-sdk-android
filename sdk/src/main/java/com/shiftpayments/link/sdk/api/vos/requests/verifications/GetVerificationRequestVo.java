package com.shiftpayments.link.sdk.api.vos.requests.verifications;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.verifications.GetVerificationStatusTask;

public class GetVerificationRequestVo extends UnauthorizedRequestVo {

    private String mVerificationId;

    public GetVerificationRequestVo(String verificationId) {
        mVerificationId = verificationId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetVerificationStatusTask(mVerificationId, shiftApiWrapper, responseHandler);
    }
}
