package com.shiftpayments.link.sdk.api.vos.responses.users;

import com.google.gson.annotations.SerializedName;

public class StartOAuthResponseVo {
    @SerializedName("type")
    public String type;

    @SerializedName("id")
    public String id;

    @SerializedName("url")
    public String url;

    @SerializedName("status")
    public String status;
}
