package me.ledge.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.views.ViewWithToolbar;
import me.ledge.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationView
        extends UserDataView<PersonalInformationView.ViewListener>
        implements ViewWithToolbar, View.OnClickListener {

    /**
     * Callbacks this {@link View} will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void emailCheckBoxClickHandler();
    }

    private TextInputLayout mFirstNameWrapper;
    private EditText mFirstNameField;

    private TextInputLayout mLastNameWrapper;
    private EditText mLastNameField;

    private TextInputLayout mEmailWrapper;
    private EditText mEmailField;
    private CheckBox mEmailAvailableCheck;
    private TextView mEmailAvailableField;

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public PersonalInformationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public PersonalInformationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mEmailField.setOnClickListener(this);
        mEmailAvailableCheck.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.cb_email_not_available) {
            mListener.emailCheckBoxClickHandler();
        }
        else {
            super.onClick(view);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();

        mFirstNameWrapper = (TextInputLayout) findViewById(R.id.til_first_name);
        mFirstNameField = (EditText) findViewById(R.id.et_first_name);

        mLastNameWrapper = (TextInputLayout) findViewById(R.id.til_last_name);
        mLastNameField = (EditText) findViewById(R.id.et_last_name);

        mEmailWrapper = (TextInputLayout) findViewById(R.id.til_email);
        mEmailField = (EditText) findViewById(R.id.et_email);

        mEmailAvailableCheck = (CheckBox) findViewById(R.id.cb_email_not_available);
        mEmailAvailableField = (TextView) findViewById(R.id.tv_email_not_available);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ((AppCompatCheckBox) mEmailAvailableCheck).setSupportButtonTintList(
                ColorStateList.valueOf(UIStorage.getInstance().getPrimaryColor()));
    }

    /**
     * @return First name as entered by the user.
     */
    public String getFirstName() {
        return mFirstNameField.getText().toString();
    }

    /**
     * Shows a new first name.
     * @param firstName New first name.
     */
    public void setFirstName(String firstName) {
        mFirstNameField.setText(firstName);
    }

    /**
     * @return Last name as entered by the user.
     */
    public String getLastName() {
        return mLastNameField.getText().toString();
    }

    /**
     * Shows a new last name.
     * @param lastName New last name.
     */
    public void setLastName(String lastName) {
        mLastNameField.setText(lastName);
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
     * Updates the first name field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateFirstNameError(boolean show, int errorMessageId) {
        updateErrorDisplay(mFirstNameWrapper, show, errorMessageId);
    }

    /**
     * Updates the last name field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateLastNameError(boolean show, int errorMessageId) {
        updateErrorDisplay(mLastNameWrapper, show, errorMessageId);
    }

    /**
     * Updates the email field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateEmailError(boolean show, int errorMessageId) {
        updateErrorDisplay(mEmailWrapper, show, errorMessageId);
    }

    public void showName(boolean show) {
        if(show) {
            mFirstNameField.setVisibility(VISIBLE);
            mLastNameField.setVisibility(VISIBLE);
        }
        else {
            mFirstNameField.setVisibility(GONE);
            mLastNameField.setVisibility(GONE);
        }
    }

    public void showEmail(boolean show) {
        if(show) {
            mEmailField.setVisibility(VISIBLE);
        }
        else {
            mEmailField.setVisibility(GONE);
        }
    }

    public void showEmailNotAvailableCheckbox(boolean show) {
        if(show) {
            mEmailAvailableCheck.setVisibility(VISIBLE);
            mEmailAvailableField.setVisibility(VISIBLE);
        }
        else {
            mEmailAvailableCheck.setVisibility(GONE);
            mEmailAvailableField.setVisibility(GONE);
        }
    }

    public boolean isEmailCheckboxChecked() {
        return mEmailAvailableCheck.isChecked();
    }

    public void enableEmailField(boolean enabled) {
        mEmailField.setEnabled(enabled);
    }

    public void checkEmailNotAvailableCheckbox(boolean enable) {
        mEmailAvailableCheck.setChecked(enable);
        enableEmailField(!enable);
    }
}
