package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

public class DataPointConfigurationVo {
    @SerializedName("type")
    public String type;

    public DataPointConfigurationVo(String type) {
        this.type = type;
    }
}
