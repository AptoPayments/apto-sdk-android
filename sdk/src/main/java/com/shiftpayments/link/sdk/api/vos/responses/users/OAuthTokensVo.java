package com.shiftpayments.link.sdk.api.vos.responses.users;

import com.google.gson.annotations.SerializedName;

public class OAuthTokensVo {
    @SerializedName("refresh")
    public String refresh;

    @SerializedName("access")
    public String access;
}
