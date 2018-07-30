package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

public class ApiErrorUtil {

    private static final SparseArray<String> mErrorMapping = createErrorMapping();

    public static void showErrorMessage(ApiErrorVo error, Context context) {
        if(!error.toString().isEmpty()) {
            String message = mErrorMapping.get(error.serverCode);
            if(message == null) {
                if(error.statusCode >= 400 && error.statusCode < 600) {
                    message = "Something went wrong.";
                    if(error.statusCode<500) {
                        message += " Error code: " + error.serverCode;
                    }
                }
                else {
                    message = context.getString(R.string.toast_api_error, error.toString());
                }
            }
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

        if(contentViewGroup != null) {
            rootView = contentViewGroup.getChildAt(0);
        }

        if(rootView == null) {
            rootView = activity.getWindow().getDecorView().getRootView();
        }

        return rootView;
    }

    private static void showSnackBar(String message, Context context) {
        final View rootView = getRootView((Activity) context);
        if(rootView != null) {
            Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG);
            snackbar.getView().setBackgroundColor(UIStorage.getInstance().getErrorColor());
            TextView snackbarTextView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            snackbarTextView.setTextSize(18);
            snackbarTextView.setMaxLines(3);
            snackbar.show();
        }
    }

    private static SparseArray<String> createErrorMapping() {
        SparseArray<String> errorsSparseArray = new SparseArray<>();
        errorsSparseArray.append(3032, "For your security, your session has timed out due to inactivity.");
        errorsSparseArray.append(3033, "For your security, your session has timed out due to inactivity.");
        errorsSparseArray.append(3040, "For your security, your session has timed out due to inactivity.");
        errorsSparseArray.append(3041, "For your security, your session has timed out due to inactivity.");
        errorsSparseArray.append(3042, "For your security, your session has timed out due to inactivity.");
        errorsSparseArray.append(90028, "Error.\nPlease re-enter your credentials.");
        errorsSparseArray.append(90032, "Error.\nPlease verify your login information.");
        errorsSparseArray.append(90117, "Oops!\nSomething went wrong. Please try again.");
        errorsSparseArray.append(90119, "Oops!\nSomething went wrong. Please try again.");
        errorsSparseArray.append(90173, "Oops!\nSomething went wrong. Please try again.");
        errorsSparseArray.append(90174, "Oops!\nSomething went wrong. Please try again.");
        errorsSparseArray.append(90175, "Oops!\nSomething went wrong. Please try again.");

        return errorsSparseArray;
    }

}
