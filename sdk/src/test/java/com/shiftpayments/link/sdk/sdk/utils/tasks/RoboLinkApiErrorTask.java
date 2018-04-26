package com.shiftpayments.link.sdk.sdk.utils.tasks;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class RoboLinkApiErrorTask extends ShiftApiTask<Void, Void, Object, Object> {

    public static final String MESSAGE = "message";
    public static final int SERVER_CODE = 666;
    public static final String SERVER_MESSAGE = "server message";

    public RoboLinkApiErrorTask(Object requestData, ShiftApiWrapper apiWrapper, ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
    }

    @Override
    protected Object callApi() throws ApiException {
        ApiErrorVo error = new ApiErrorVo();
        error.serverCode = SERVER_CODE;
        error.serverMessage = SERVER_MESSAGE;

        throw new ApiException(error, MESSAGE);
    }
}
