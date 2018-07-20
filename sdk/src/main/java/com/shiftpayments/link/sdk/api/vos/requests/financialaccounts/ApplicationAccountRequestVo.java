package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationAccountType;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.loanapplication.SetApplicationAccountTask;

/**
 * Request data to link a new account to an application.
 * @author Adrian
 */
public class ApplicationAccountRequestVo extends UnauthorizedRequestVo {

    @SerializedName("account_id")
    public String accountId;

    /**
     * Action type.
     * @see LoanApplicationAccountType
     */
    @SerializedName("account_type")
    public int accountType;

    public String applicationId;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new SetApplicationAccountTask(this, applicationId, shiftApiWrapper, responseHandler);
    }
}
