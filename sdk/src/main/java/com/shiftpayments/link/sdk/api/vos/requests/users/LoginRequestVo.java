package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.LoginUserTask;

/**
 * Request data for the login API call.
 * @author Adrian
 */
public class LoginRequestVo extends UnauthorizedRequestVo {
    public ListRequestVo<VerificationVo[]> verifications;

    public LoginRequestVo(ListRequestVo<VerificationVo[]> verifications) {
        this.verifications = verifications;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new LoginUserTask(this, shiftApiWrapper, responseHandler);
    }
}
