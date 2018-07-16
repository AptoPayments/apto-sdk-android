package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.ui.R;

public class ApiErrorUtil {
    //TODO: map ApiErrors with friendly error messages
    public static void showErrorMessage(ApiErrorVo error, Context context) {
        if(!error.toString().isEmpty()) {
            String message = context.getString(R.string.toast_api_error, error.toString());
            showSnackBar(message, context);
        }
    }

    public static void showErrorMessage(Throwable exception, Context context) {
        if(!exception.getMessage().isEmpty()) {
            String message = context.getString(R.string.toast_api_error, exception.getMessage());
            showSnackBar(message, context);
        }
    }

    public static void showErrorMessage(SessionExpiredErrorVo error, Context context) {
        if(!error.toString().isEmpty()) {
            String message = context.getResources().getString(R.string.session_expired_error);
            showSnackBar(message, context);
        }
    }

    public static void showErrorMessage(String message, Context context) {
        if(!message.isEmpty()) {
            showSnackBar(message, context);
        }
    }

    private static View getRootView(Activity activity) {
        final ViewGroup contentViewGroup = activity.findViewById(android.R.id.content);
        View rootView = null;

        if(contentViewGroup != null)
            rootView = contentViewGroup.getChildAt(0);

        if(rootView == null)
            rootView = activity.getWindow().getDecorView().getRootView();

        return rootView;
    }

    private static void showSnackBar(String message, Context context) {
        final View rootView = getRootView((Activity) context);
        if(rootView != null)
            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

}
