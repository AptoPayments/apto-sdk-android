package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;

public class DisclaimerConfiguration {

    @SerializedName("type")
    public String type;

    @SerializedName("disclaimer")
    public ContentVo disclaimer;

}
