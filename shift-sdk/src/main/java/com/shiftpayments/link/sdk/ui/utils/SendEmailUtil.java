package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class SendEmailUtil {

    private String mTargetEmail = "support@shiftpayments.com";

    public SendEmailUtil(String targetEmail) {
        if(targetEmail != null && !targetEmail.isEmpty()){
            mTargetEmail = targetEmail;
        }
    }

    public void execute(Activity activity) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"+mTargetEmail));
        try {
            activity.startActivity(emailIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
