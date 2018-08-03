package com.shiftpayments.link.sdk.api.vos.responses.workflow;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 18/10/2017.
 */

public class ActionConfigurationVo {

    @SerializedName("type")
    public String type;

    public ActionConfigurationVo(String type) {
        this.type = type;
    }
}
