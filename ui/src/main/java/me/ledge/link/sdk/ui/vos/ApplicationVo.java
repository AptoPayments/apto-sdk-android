package me.ledge.link.sdk.ui.vos;

import me.ledge.link.api.vos.responses.workflow.ActionVo;
import me.ledge.link.sdk.ui.workflow.WorkflowObject;

/**
 * Created by adrian on 09/11/2017.
 */

public class ApplicationVo extends WorkflowObject{

    public ApplicationVo(String workflowObjectId, ActionVo nextAction) {
        super(workflowObjectId, nextAction);
    }
}
