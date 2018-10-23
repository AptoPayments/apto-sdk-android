package com.shiftpayments.link.sdk.api.vos.requests.cardapplication;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.cardapplication.CancelCardApplicationTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class CancelCardApplicationRequest extends UnauthorizedRequestVo {

    private String mCardId;

    public CancelCardApplicationRequest(final String cardId) {
        this.mCardId = cardId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new CancelCardApplicationTask(this, mCardId, shiftApiWrapper, responseHandler);
    }

}
