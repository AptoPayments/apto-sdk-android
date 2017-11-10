package me.ledge.link.sdk.ui.workflow;

import android.app.Activity;

import me.ledge.link.sdk.ui.presenters.fundingaccountselector.FundingAccountSelectorModule;

import static me.ledge.link.api.utils.workflow.WorkflowActionType.SELECT_FUNDING_ACCOUNT;
import static me.ledge.link.api.utils.workflow.WorkflowActionType.SHOW_GENERIC_MESSAGE;

/**
 * Created by adrian on 09/11/2017.
 */

public class WorkflowModule extends LedgeBaseModule {
    private WorkflowObject mWorkFlowObject;
    public Command onBack;

    public WorkflowModule(Activity activity, WorkflowObject workflowObject) {
        super(activity);
        mWorkFlowObject = workflowObject;
    }

    @Override
    public void initialModuleSetup() {
        // TODO: factory
        String actionType = mWorkFlowObject.nextAction.actionType;
        if(actionType.equals(SELECT_FUNDING_ACCOUNT) || actionType.equals(SHOW_GENERIC_MESSAGE)) {
            FundingAccountSelectorModule fundingAccountSelectorModule = FundingAccountSelectorModule.getInstance(this.getActivity());
            fundingAccountSelectorModule.onBack = onBack;
            startModule(fundingAccountSelectorModule);
        }

    }
}
