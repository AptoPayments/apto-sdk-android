package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.ui.activities.DisclaimerActivity;
import com.shiftpayments.link.sdk.ui.workflow.Command;

/**
 * Disclaimer helper class.
 * @author Adrian
 */

public class DisclaimerUtil {

    public static Command onAccept;
    public static String workflowId;
    public static String actionId;

    public static void showDisclaimer(Activity activity, ContentVo disclaimer, Command onFinish,
                                      String workflowId, String actionId) {
        onAccept = onFinish;
        DisclaimerUtil.workflowId = workflowId;
        DisclaimerUtil.actionId = actionId;
        activity.startActivity(DisclaimerActivity.getDisclaimerIntent(activity, disclaimer));
    }
}
