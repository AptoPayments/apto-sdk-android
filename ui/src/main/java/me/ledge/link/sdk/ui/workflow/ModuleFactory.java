package me.ledge.link.sdk.ui.workflow;

import android.app.Activity;

import me.ledge.link.api.vos.responses.workflow.ActionVo;
import me.ledge.link.sdk.ui.presenters.fundingaccountselector.FundingAccountSelectorModule;

import static me.ledge.link.api.utils.workflow.WorkflowActionType.SELECT_FUNDING_ACCOUNT;
import static me.ledge.link.api.utils.workflow.WorkflowActionType.SHOW_GENERIC_MESSAGE;

/**
 * Created by adrian on 13/11/2017.
 */
public class ModuleFactory {
    public static LedgeBaseModule getModule(Activity activity, ActionVo action) {
        switch(action.actionType) {
            case SELECT_FUNDING_ACCOUNT:
            case SHOW_GENERIC_MESSAGE:
                return FundingAccountSelectorModule.getInstance(activity);
            default:
                return null;
        }
    }
}
