package com.shiftpayments.link.sdk.ui.workflow;

import android.app.Activity;

/**
 * Created by adrian on 09/11/2017.
 */

public class WorkflowModule extends ShiftBaseModule {
    protected WorkflowObject mWorkFlowObject;
    private Command onWorkflowModuleFinish;
    private WorkflowObjectStatusInterface getWorkflowObjectStatus;

    public WorkflowModule(Activity activity, WorkflowObject workflowObject, WorkflowObjectStatusInterface getWorkflowObjectStatus) {
        super(activity);
        mWorkFlowObject = workflowObject;
        this.getWorkflowObjectStatus = getWorkflowObjectStatus;
    }

    @Override
    public void initialModuleSetup() {
        onWorkflowModuleFinish = () -> {
            mWorkFlowObject = getWorkflowObjectStatus.getApplication(mWorkFlowObject);
            startNextModule();
        };
        startNextModule();
    }

    public void startNextModule() {
        ShiftBaseModule module = ModuleFactory.getModule(this.getActivity(), mWorkFlowObject.nextAction);
        module.onBack = this.onBack;
        module.onFinish = this.onWorkflowModuleFinish;
        startModule(module);
    }

}
