package com.shift.link.sdk.ui.workflow;

import android.app.Activity;

import com.shift.link.sdk.api.vos.responses.workflow.ActionVo;
import com.shift.link.sdk.api.vos.responses.workflow.GenericMessageConfigurationVo;
import com.shift.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shift.link.sdk.ui.presenters.fundingaccountselector.FundingAccountSelectorModule;
import com.shift.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageModule;

import static com.shift.link.sdk.api.utils.workflow.WorkflowActionType.SELECT_FUNDING_ACCOUNT;
import static com.shift.link.sdk.api.utils.workflow.WorkflowActionType.SHOW_GENERIC_MESSAGE;

/**
 * Created by adrian on 13/11/2017.
 */
public class ModuleFactory {
    public static LedgeBaseModule getModule(Activity activity, ActionVo action) {
        switch(action.actionType) {
            case SELECT_FUNDING_ACCOUNT:
                return FundingAccountSelectorModule.getInstance(activity, (SelectFundingAccountConfigurationVo)action.configuration);
            case SHOW_GENERIC_MESSAGE:
                return ShowGenericMessageModule.getInstance(activity, (GenericMessageConfigurationVo)action.configuration);
            default:
                return new ActionNotSupportedModule(activity);
        }
    }
}
