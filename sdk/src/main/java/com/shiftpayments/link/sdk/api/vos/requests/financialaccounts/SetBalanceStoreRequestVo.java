package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to set the balance store.
 * @author Adrian
 */
public class SetBalanceStoreRequestVo extends UnauthorizedRequestVo {
    @SerializedName("custodian_type")
    public String type;

    public CredentialVo credential;

    public SetBalanceStoreRequestVo(String type, CredentialVo credential) {
        this.type = type;
        this.credential = credential;
    }

}
