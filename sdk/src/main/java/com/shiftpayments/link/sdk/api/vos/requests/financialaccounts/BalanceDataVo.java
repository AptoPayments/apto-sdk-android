package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * Balance data
 * @author Adrian
 */
public class BalanceDataVo {

    @SerializedName("custodian_type")
    public String custodianType;

    @SerializedName("credential_type")
    public String credentialType;

    @SerializedName("oauth_token")
    public String oauthToken;

    @SerializedName("refresh_token")
    public String refreshToken;

    public BalanceDataVo(String custodianType, String oauthToken, String refreshToken) {
        this.custodianType = custodianType;
        this.oauthToken = oauthToken;
        this.refreshToken = refreshToken;
        credentialType = "oauth";
    }
}
