package me.ledge.link.sdk.ui.workflow;

import android.app.Activity;

/**
 * Created by adrian on 09/11/2017.
 */

public class WorkflowModule extends LedgeBaseModule {
    private WorkflowObject mWorkFlowObject;

    public WorkflowModule(Activity activity, WorkflowObject workflowObject) {
        super(activity);
        mWorkFlowObject = workflowObject;
    }

    @Override
    public void initialModuleSetup() {
        LedgeBaseModule module = ModuleFactory.getModule(this.getActivity(), mWorkFlowObject.nextAction.actionType);
        module.onBack = this.onBack;
        module.onFinish = this.onFinish;
        startModule(module);
    }

}
