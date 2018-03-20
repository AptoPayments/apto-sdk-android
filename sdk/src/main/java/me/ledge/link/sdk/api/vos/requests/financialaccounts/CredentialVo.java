package me.ledge.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * Credential data
 * @author Adrian
 */
public class CredentialVo {

    @SerializedName("credential_type")
    public String type;

    public CredentialVo(String type) {
        this.type = type;
    }
}
