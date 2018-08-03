package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.UpdateFinancialAccountPinTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Created by pauteruel on 01/03/2018.
 */

public class UpdateFinancialAccountPinRequestVo extends UnauthorizedRequestVo {
    public String type = "pin";

    public String accountId;

    /**
     * Manage pin of a financial account
     */
    @SerializedName("pin")
    public String pin;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new UpdateFinancialAccountPinTask(this, accountId, shiftApiWrapper, responseHandler);
    }
}