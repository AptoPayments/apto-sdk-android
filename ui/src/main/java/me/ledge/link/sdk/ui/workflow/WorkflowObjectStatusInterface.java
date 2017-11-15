package me.ledge.link.sdk.ui.workflow;

import me.ledge.link.sdk.ui.vos.ApplicationVo;

/**
 * Created by adrian on 14/11/2017.
 */

public interface WorkflowObjectStatusInterface {
    ApplicationVo getApplication(WorkflowObject currentObject);
}
