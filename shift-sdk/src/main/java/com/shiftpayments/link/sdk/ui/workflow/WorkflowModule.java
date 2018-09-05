package com.shiftpayments.link.sdk.ui.workflow;

import android.app.Activity;

/**
 * Created by adrian on 09/11/2017.
 */

public class WorkflowModule extends ShiftBaseModule {
    protected WorkflowObject mWorkFlowObject;
    private Command onWorkflowModuleFinish;
    protected WorkflowObjectStatusInterface getWorkflowObjectStatus;

    public WorkflowModule(Activity activity, WorkflowObject workflowObject,
                           WorkflowObjectStatusInterface getWorkflowObjectStatus, Command onFinish,
                           Command onBack) {
        super(activity, onFinish, onBack);
        mWorkFlowObject = workflowObject;
        this.getWorkflowObjectStatus = getWorkflowObjectStatus;
    }

    public WorkflowModule(Activity activity, WorkflowObject workflowObject,
                          WorkflowObjectStatusInterface getWorkflowObjectStatus, Command onFinish,
                          Command onBack, Command onError) {
        super(activity, onFinish, onBack, onError);
        mWorkFlowObject = workflowObject;
        this.getWorkflowObjectStatus = getWorkflowObjectStatus;
    }

    public WorkflowModule(Activity activity, WorkflowObjectStatusInterface getWorkflowObjectStatus, Command onFinish, Command onBack, Command onError) {
        this(activity, null, getWorkflowObjectStatus, onFinish, onBack, onError);
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
        ShiftBaseModule module = ModuleFactory.getModule(this.getActivity(), mWorkFlowObject.nextAction, onWorkflowModuleFinish, onBack, onError);
        startModule(module);
    }
}
