package com.shift.link.sdk.api.vos.responses.workflow;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 18/10/2017.
 */

public class ActionVo {

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

    /**
     * Workflow action type
     * @see com.shift.link.sdk.api.utils.workflow.WorkflowActionType
     */
    @SerializedName("action_type")
    public String actionType;

    @SerializedName("owner_module")
    public String ownerModule;

    @SerializedName("configuration")
    public ActionConfigurationVo configuration;
}
