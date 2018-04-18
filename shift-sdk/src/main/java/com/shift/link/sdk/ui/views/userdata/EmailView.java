package com.shift.link.sdk.ui.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.views.ViewWithToolbar;
import com.shift.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Created by pauteruel on 19/02/2018.
 */

public class EmailView
        extends UserDataView<EmailView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {}

    private TextInputLayout mEmailWrapper;
    private EditText mEmailField;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public EmailView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public EmailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mEmailWrapper = (TextInputLayout) findViewById(R.id.til_email);
        mEmailField = (EditText) findViewById(R.id.et_email);
    }

    /**
     * @return Email address as entered by the user.
     */
    public String getEmail() {
        return mEmailField.getText().toString();
    }

    /**
     * Shows a new email address.
     * @param email New email address.
     */
    public void setEmail(String email) {
        mEmailField.setText(email);
    }

    /**
     * Updates the email field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateEmailError(boolean show, int errorMessageId) {
        updateErrorDisplay(mEmailWrapper, show, errorMessageId);
    }

}
