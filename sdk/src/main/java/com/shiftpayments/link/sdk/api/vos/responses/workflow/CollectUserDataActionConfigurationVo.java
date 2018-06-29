package com.shiftpayments.link.sdk.api.vos.responses.workflow;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.utils.workflow.WorkflowConfigType;
import com.shiftpayments.link.sdk.api.vos.responses.config.DataPointGroupsListVo;

public class CollectUserDataActionConfigurationVo extends ActionConfigurationVo {

    @SerializedName("required_datapoint_groups")
    public DataPointGroupsListVo requiredDataPointsList;

    public CollectUserDataActionConfigurationVo(DataPointGroupsListVo requiredDataPointsList) {
        super(WorkflowConfigType.COLLECT_USER_DATA_CONFIG);
        this.requiredDataPointsList = requiredDataPointsList;
    }
}
