package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;
import com.shiftpayments.link.sdk.ui.activities.DisclaimerActivity;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.Command;

import static com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo.formatValues.external_url;

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
        if(ContentVo.formatValues.valueOf(disclaimer.format).equals(external_url)) {
            disclaimer.value = parseUrl(disclaimer.value);
        }
        activity.startActivity(DisclaimerActivity.getDisclaimerIntent(activity, disclaimer));
    }

    private static String parseUrl(String url) {
        Address userAddress = (Address) UserStorage.getInstance().getUserData().getUniqueDataPoint(
                DataPointVo.DataPointType.Address, null);
        if(userAddress != null) {
            url = url.replace("[language]", LanguageUtil.getLanguage()).replace("[state]", userAddress.stateCode.toUpperCase());
        }
        return url;
    }
}
