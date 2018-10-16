package com.shiftpayments.link.sdk.api.vos.responses.users;

import com.google.gson.annotations.SerializedName;

public class OAuthStatusResponseVo {
    @SerializedName("type")
    public String type;

    @SerializedName("id")
    public String id;

    @SerializedName("status")
    public String status;

    @SerializedName("tokens")
    public OAuthTokensVo tokens;

    @SerializedName("user_data")
    public UserDataListResponseVo userDataListVo;
}
