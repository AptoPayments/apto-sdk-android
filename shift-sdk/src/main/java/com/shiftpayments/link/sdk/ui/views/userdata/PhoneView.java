package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the phone screen.
 * @author Adrian
 */
public class PhoneView
        extends UserDataView<PhoneView.ViewListener>
        implements ViewWithToolbar {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {}

    private TextInputLayout mPhoneWrapper;
    private EditText mPhoneField;
    private TextView mPhoneLabel;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public PhoneView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public PhoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mPhoneWrapper = findViewById(R.id.til_phone);
        mPhoneField = findViewById(R.id.et_phone);
        mPhoneLabel = findViewById(R.id.tv_phone_header);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPhoneField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mPhoneField.requestFocus();
    }

    @Override
    protected void setupListeners() {
        super.setupListeners();
        mPhoneField.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mListener.nextClickHandler();
                return true;
            }
            return false;
        });
        super.setUiFieldsObservable(mPhoneField);
    }

    /**
     * @return Phone number as entered by the user.
     */
    public String getPhone() {
        return mPhoneField.getText().toString();
    }

    /**
     * Shows a new phone number.
     * @param phone New phone number.
     */
    public void setPhone(String phone) {
        mPhoneField.setText(phone);
    }

    /**
     * Updates the phone field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updatePhoneError(boolean show, int errorMessageId) {
        updateErrorDisplay(mPhoneWrapper, show, errorMessageId);
    }

    /**
     * Shows the label.
     * @param description The text to set in the label.
     */
    public void setDescription(String description) {
        mPhoneLabel.setText(description);
    }
}
