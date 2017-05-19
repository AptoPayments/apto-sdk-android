package me.ledge.link.sdk.ui.utils;

import android.app.Activity;
import android.content.Intent;

import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.activities.DisclaimerActivity;

/**
 * Disclaimer helper class.
 * @author Adrian
 */

public class DisclaimerUtil {
    public static String disclaimerURL;
    public static Command onAccept;

    public static void loadExternalURL(Activity activity, String URL, Command onFinish) {
        disclaimerURL = URL;
        onAccept = onFinish;
        activity.startActivity(new Intent(activity, DisclaimerActivity.class));
    }
}
