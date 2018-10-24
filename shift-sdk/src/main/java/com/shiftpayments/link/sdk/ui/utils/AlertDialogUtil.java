package com.shiftpayments.link.sdk.ui.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

public class AlertDialogUtil {

    private Activity mActivity;

    public AlertDialogUtil(final Activity activity) {
        mActivity = activity;
    }

    public AlertDialog getAlertDialog(final String alertTitle, final String alertMessage,
                                      final OnPositiveCallback positiveCallback, final OnNegativeCallback negativeCallback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(UIStorage.getInstance().getTextPrimaryColor());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(alertTitle);
        spannableStringBuilder.setSpan(
                foregroundColorSpan,
                0,
                alertTitle.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setTitle(spannableStringBuilder);

        foregroundColorSpan = new ForegroundColorSpan(UIStorage.getInstance().getTextSecondaryColor());
        spannableStringBuilder = new SpannableStringBuilder(alertMessage);
        spannableStringBuilder.setSpan(
                foregroundColorSpan,
                0,
                alertMessage.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        builder.setMessage(spannableStringBuilder);

        builder.setNegativeButton(mActivity.getString(R.string.disclaimer_alert_cancel), (dialog, id) -> negativeCallback.onNegativeButtonPressed());
        builder.setPositiveButton(mActivity.getString(R.string.disclaimer_accept), (dialog, id) -> positiveCallback.onPositiveButtonPressed());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(UIStorage.getInstance().getTextPrimaryColor());
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(UIStorage.getInstance().getUiPrimaryColor());
        });
        return dialog;
    }

    public interface OnPositiveCallback {

        void onPositiveButtonPressed();

    }

    public interface OnNegativeCallback {

        void onNegativeButtonPressed();

    }
}
