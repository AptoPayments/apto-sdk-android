package com.shiftpayments.link.sdk.api.vos.requests.cardapplication;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.cardapplication.CreateCardApplicationTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class CreateCardApplicationRequestVo extends UnauthorizedRequestVo {

    @SerializedName("card_product_id")
    String cardProductId;

    public CreateCardApplicationRequestVo(String cardProductId) {
        this.cardProductId = cardProductId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new CreateCardApplicationTask(cardProductId, shiftApiWrapper, responseHandler);
    }
}
