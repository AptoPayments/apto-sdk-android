package me.ledge.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * Custodian data
 * @author Adrian
 */
public class CustodianVo {

    @SerializedName("custodian_type")
    public String type;

    public CredentialVo credential;

    public CustodianVo(String type, CredentialVo credential) {
        this.type = type;
        this.credential = credential;
    }
}
