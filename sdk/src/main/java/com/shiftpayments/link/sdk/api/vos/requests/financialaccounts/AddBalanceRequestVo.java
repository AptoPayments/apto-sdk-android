package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.AddBalanceTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class AddBalanceRequestVo extends UnauthorizedRequestVo {

    @SerializedName("funding_source_type")
    public String fundingSourceType;

    @SerializedName("funding_source_data")
    public BalanceDataVo balanceData;

    public AddBalanceRequestVo(BalanceDataVo balanceData) {
        this.balanceData = balanceData;
        fundingSourceType = "custodian_wallet";
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new AddBalanceTask(this, shiftApiWrapper, responseHandler);
    }
}
