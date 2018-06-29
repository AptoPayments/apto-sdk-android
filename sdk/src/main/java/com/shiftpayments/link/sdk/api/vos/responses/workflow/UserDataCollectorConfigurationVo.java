package com.shiftpayments.link.sdk.api.vos.responses.workflow;

import com.shiftpayments.link.sdk.api.utils.workflow.WorkflowConfigType;

/**
 * Created by adrian on 18/10/2017.
 */

public class UserDataCollectorConfigurationVo extends ActionConfigurationVo {

    public String title;
    public CallToActionVo callToAction;

    public UserDataCollectorConfigurationVo(String title, CallToActionVo callToAction) {
        super(WorkflowConfigType.COLLECT_USER_DATA_CONFIG);
        this.title = title;
        this.callToAction = callToAction;
    }
}
