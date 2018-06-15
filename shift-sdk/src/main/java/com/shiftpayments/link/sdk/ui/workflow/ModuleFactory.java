package com.shiftpayments.link.sdk.ui.workflow;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.GenericMessageConfigurationVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shiftpayments.link.sdk.ui.presenters.fundingaccountselector.FundingAccountSelectorModule;
import com.shiftpayments.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageModule;

import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.SELECT_FUNDING_ACCOUNT;
import static com.shiftpayments.link.sdk.api.utils.workflow.WorkflowActionType.SHOW_GENERIC_MESSAGE;

/**
 * Created by adrian on 13/11/2017.
 */
public class ModuleFactory {
    public static ShiftBaseModule getModule(Activity activity, ActionVo action, Command onFinish,
                                            Command onBack) {
        switch(action.actionType) {
            case SELECT_FUNDING_ACCOUNT:
                return FundingAccountSelectorModule.getInstance(activity, onFinish, onBack, (SelectFundingAccountConfigurationVo)action.configuration);
            case SHOW_GENERIC_MESSAGE:
                return ShowGenericMessageModule.getInstance(activity, onFinish, onBack, (GenericMessageConfigurationVo)action.configuration);
            default:
                return new ActionNotSupportedModule(activity, onFinish, onBack);
        }
    }
}
