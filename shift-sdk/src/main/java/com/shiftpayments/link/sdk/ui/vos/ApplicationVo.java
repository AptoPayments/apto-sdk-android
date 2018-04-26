package com.shiftpayments.link.sdk.ui.vos;

import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionVo;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowObject;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowObject;

/**
 * Created by adrian on 09/11/2017.
 */

public class ApplicationVo extends WorkflowObject {

    public ApplicationVo(String workflowObjectId, ActionVo nextAction) {
        super(workflowObjectId, nextAction);
    }
}
