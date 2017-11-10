package me.ledge.link.sdk.ui.workflow;

import me.ledge.link.api.vos.responses.workflow.ActionVo;

/**
 * Created by adrian on 09/11/2017.
 */

public abstract class WorkflowObject {
    public String workflowObjectId;
    public ActionVo nextAction;

    public WorkflowObject(String workflowObjectId, ActionVo nextAction) {
        this.workflowObjectId = workflowObjectId;
        this.nextAction = nextAction;
    }
}
