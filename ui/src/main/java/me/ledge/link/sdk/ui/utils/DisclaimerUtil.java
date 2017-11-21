package me.ledge.link.sdk.ui.utils;

import android.app.Activity;
import android.content.Intent;

import me.ledge.link.api.vos.responses.config.ContentVo;
import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.activities.DisclaimerActivity;

/**
 * Disclaimer helper class.
 * @author Adrian
 */

public class DisclaimerUtil {
    public static ContentVo disclaimer;
    public static Command onAccept;

    public static void showDisclaimer(Activity activity, ContentVo disclaimerVo, Command onFinish) {
        disclaimer = disclaimerVo;
        onAccept = onFinish;
        activity.startActivity(new Intent(activity, DisclaimerActivity.class));
    }
}
