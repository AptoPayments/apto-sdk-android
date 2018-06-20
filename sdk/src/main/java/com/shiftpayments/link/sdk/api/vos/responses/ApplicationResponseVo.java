package com.shiftpayments.link.sdk.api.vos.responses;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionVo;

public class ApplicationResponseVo {

    public enum ApplicationType {
        LOAN,
        CARD
    }

    public String type;

    public String id;

    @SerializedName("application_type")
    public ApplicationType applicationType;

    @SerializedName("application_data")
    public String applicationData;

    public String status;

    @SerializedName("create_time")
    public float createTime;

    @SerializedName("next_action")
    public ActionVo nextAction;
}
