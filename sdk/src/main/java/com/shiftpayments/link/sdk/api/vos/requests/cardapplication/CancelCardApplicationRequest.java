package com.shiftpayments.link.sdk.api.vos.requests.cardapplication;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.cardapplication.CancelCardApplicationTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class CancelCardApplicationRequest extends UnauthorizedRequestVo {

    private String mApplicationId;

    public CancelCardApplicationRequest(final String applicationId) {
        this.mApplicationId = applicationId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new CancelCardApplicationTask(this, mApplicationId, shiftApiWrapper, responseHandler);
    }

}
