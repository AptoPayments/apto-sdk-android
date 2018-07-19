package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.UpdateUserTask;

/**
 * Request data to update a user.
 * @author Wijnand
 */
public class UpdateUserRequestVo extends UnauthorizedRequestVo {

    private DataPointList data;

    public UpdateUserRequestVo(DataPointList data) {
        this.data = data;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new UpdateUserTask(data, shiftApiWrapper, responseHandler);
    }
}
