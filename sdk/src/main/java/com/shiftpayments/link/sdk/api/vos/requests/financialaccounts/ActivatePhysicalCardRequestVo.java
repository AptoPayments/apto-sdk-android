package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.ActivatePhysicalCardTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class ActivatePhysicalCardRequestVo extends UnauthorizedRequestVo {

    private String mCardId;

    @SerializedName("code")
    private String mActivationCode;

    public ActivatePhysicalCardRequestVo(String accountId, String activationCode) {
        mCardId = accountId;
        mActivationCode = activationCode;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new ActivatePhysicalCardTask(this, mCardId, shiftApiWrapper, responseHandler);
    }
}
