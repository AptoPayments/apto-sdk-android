package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionConfigurationVo;

import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowConfigType.SHOW_DISCLAIMER_CONFIG;

public class DisclaimerConfiguration extends ActionConfigurationVo {

    @SerializedName("type")
    public String type;

    @SerializedName("disclaimer")
    public ContentVo disclaimer;

    public DisclaimerConfiguration(String type, ContentVo disclaimer) {
        super(SHOW_DISCLAIMER_CONFIG);
        this.type = type;
        this.disclaimer = disclaimer;
    }
}
