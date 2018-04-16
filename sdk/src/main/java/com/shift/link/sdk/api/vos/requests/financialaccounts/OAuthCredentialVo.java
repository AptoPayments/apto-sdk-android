package com.shift.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * oAuth credential data
 * @author Adrian
 */
public class OAuthCredentialVo extends CredentialVo {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("refresh_token")
    public String refreshToken;

    public OAuthCredentialVo(String accessToken, String refreshToken) {
        super("oauth");
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
