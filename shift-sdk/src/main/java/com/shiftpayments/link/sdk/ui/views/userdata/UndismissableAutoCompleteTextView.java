package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.util.AttributeSet;

import java.lang.reflect.Method;

public class UndismissableAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public UndismissableAutoCompleteTextView(Context context) {
        super(context);
    }

    public UndismissableAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UndismissableAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean setForceIgnoreOutsideTouchWithReflexion(boolean forceIgnoreOutsideTouch) {
        try {
            Method method = android.widget.AutoCompleteTextView.class.getMethod("setForceIgnoreOutsideTouch", boolean.class);
            method.invoke(this, forceIgnoreOutsideTouch);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
