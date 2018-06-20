package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

import com.google.gson.annotations.SerializedName;

public class DisclaimerAction {

    @SerializedName("type")
    public String type;
    @SerializedName("action_id")
    public String actionId;
    @SerializedName("name")
    public String name;
    @SerializedName("order")
    public Integer order;
    @SerializedName("status")
    public Integer status;
    @SerializedName("editable_status")
    public Boolean editableStatus;
    @SerializedName("action_type")
    public String actionType;
    @SerializedName("owner_module")
    public String ownerModule;
    @SerializedName("configuration")
    public DisclaimerConfiguration configuration;

}
