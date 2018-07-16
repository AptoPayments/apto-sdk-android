package com.shiftpayments.link.sdk.ui.presenters.showdisclaimer.showgenericmessage;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.cardconfig.DisclaimerConfiguration;
import com.shiftpayments.link.sdk.ui.utils.DisclaimerUtil;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

public class ShowDisclaimerModule extends ShiftBaseModule {

    private static ShowDisclaimerModule mInstance;
    private static DisclaimerConfiguration mConfig;
    private String mApplicationId;
    private String mWorkflowId;
    private String mActionId;

    public static synchronized ShowDisclaimerModule getInstance(
            Activity activity, Command onFinish, Command onBack, DisclaimerConfiguration
            actionConfig, String applicationId, String workflowId, String actionId) {
        mConfig = actionConfig;
        if (mInstance == null) {
            mInstance = new ShowDisclaimerModule(activity, onFinish, onBack);
        }
        mInstance.mApplicationId = applicationId;
        mInstance.mWorkflowId = workflowId;
        mInstance.mActionId = actionId;
        return mInstance;
    }

    private ShowDisclaimerModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
    }

    @Override
    public void initialModuleSetup() {
        DisclaimerUtil.showDisclaimer(getActivity(), mConfig.disclaimer, onFinish, mApplicationId,
                mWorkflowId, mActionId);
    }

    public DisclaimerConfiguration getConfig() {
        return mConfig;
    }
}
