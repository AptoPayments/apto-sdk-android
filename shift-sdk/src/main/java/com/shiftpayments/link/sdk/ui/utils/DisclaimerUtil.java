package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.ui.activities.DisclaimerActivity;
import com.shiftpayments.link.sdk.ui.workflow.Command;

/**
 * Disclaimer helper class.
 * @author Adrian
 */

public class DisclaimerUtil {
    public static ContentVo disclaimer;
    public static Command onAccept;
    public static String workflowId;
    public static String actionId;

    public static void showDisclaimer(Activity activity, ContentVo disclaimerVo, Command onFinish,
                                      String workflowId, String actionId) {
        disclaimer = disclaimerVo;
        onAccept = onFinish;
        DisclaimerUtil.workflowId = workflowId;
        DisclaimerUtil.actionId = actionId;
        activity.startActivity(new Intent(activity, DisclaimerActivity.class));
    }
}
