package com.shiftpayments.link.sdk.ui.utils;

import android.content.Context;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.ui.R;

public class ApiErrorUtil {
    //TODO: map ApiErrors with friendly error messages
    public static void showErrorMessage(ApiErrorVo error, Context context) {
        if(!error.toString().isEmpty()) {
            String message = context.getString(R.string.toast_api_error, error.toString());
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showErrorMessage(Throwable exception, Context context) {
        if(!exception.getMessage().isEmpty()) {
            String message = context.getString(R.string.toast_api_error, exception.getMessage());
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showErrorMessage(SessionExpiredErrorVo error, Context context) {
        if(!error.toString().isEmpty()) {
            String message = context.getResources().getString(R.string.session_expired_error);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showErrorMessage(String message, Context context) {
        if(!message.isEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }


}
