package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.CreateUserTask;

/**
 * Request data to create a new user.
 * @author Wijnand
 */
public class CreateUserRequestVo extends UnauthorizedRequestVo {

    private DataPointList data;

    public CreateUserRequestVo(DataPointList data) {
        this.data = data;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new CreateUserTask(data, shiftApiWrapper, responseHandler);
    }
}
