package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.AddBankAccountTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Request data to add a new bank account.
 * @author Adrian
 */
public class AddBankAccountRequestVo extends UnauthorizedRequestVo {

    public String type = "bank_account";

    /**
     * Token received from Plaid.
     */
    @SerializedName("public_token")
    public String publicToken;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new AddBankAccountTask(this, shiftApiWrapper, responseHandler);
    }
}
