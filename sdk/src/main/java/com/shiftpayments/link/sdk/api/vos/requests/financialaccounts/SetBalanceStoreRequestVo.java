package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.SetBalanceStoreTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Request data to set the balance store.
 * @author Adrian
 */
public class SetBalanceStoreRequestVo extends UnauthorizedRequestVo {

    public String applicationId;

    @SerializedName("custodian_type")
    public String type;

    public CredentialVo credential;

    public SetBalanceStoreRequestVo(String type, CredentialVo credential) {
        this.type = type;
        this.credential = credential;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new SetBalanceStoreTask(applicationId, this, shiftApiWrapper, responseHandler);
    }
}
