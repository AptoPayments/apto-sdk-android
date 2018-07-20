package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.SetAccountFundingSourceTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class SetFundingSourceRequestVo extends UnauthorizedRequestVo {

    public String accountId;

    @SerializedName("funding_source_id")
    public String fundingSourceId;

    public SetFundingSourceRequestVo(String fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new SetAccountFundingSourceTask(accountId, fundingSourceId, shiftApiWrapper, responseHandler);
    }
}
