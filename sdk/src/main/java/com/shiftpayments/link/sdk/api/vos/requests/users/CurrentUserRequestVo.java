package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.GetCurrentUserTask;

public class CurrentUserRequestVo extends UnauthorizedRequestVo {

    public boolean throwSessionExpiredError;

    public CurrentUserRequestVo(boolean throwSessionExpiredError) {
        this.throwSessionExpiredError = throwSessionExpiredError;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetCurrentUserTask(this, shiftApiWrapper, responseHandler);
    }
}
