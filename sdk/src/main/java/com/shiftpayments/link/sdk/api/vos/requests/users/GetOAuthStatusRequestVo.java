package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.GetOAuthStatusTask;

/**
 * Request data to get oAuth status.
 * @author Adrian
 */
public class GetOAuthStatusRequestVo extends UnauthorizedRequestVo {
    public String id;

    public GetOAuthStatusRequestVo(String id) {
        this.id = id;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetOAuthStatusTask(id, shiftApiWrapper, responseHandler);
    }
}
