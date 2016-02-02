package us.ledge.line.sdk.sdk.views.userdata;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.views.ViewWithToolbar;

/**
 * Displays the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationView
        extends UserDataView<NextButtonListener>
        implements ViewWithToolbar, View.OnClickListener {

    private TextInputLayout mFirstNameWrapper;
    private EditText mFirstNameField;

    private TextInputLayout mLastNameWrapper;
    private EditText mLastNameField;

    private TextInputLayout mEmailWrapper;
    private EditText mEmailField;

    private TextInputLayout mPhoneWrapper;
    private EditText mPhoneField;

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
    protected void findAllViews() {
        super.findAllViews();

        mFirstNameWrapper = (TextInputLayout) findViewById(R.id.til_first_name);
        mFirstNameField = (EditText) findViewById(R.id.et_first_name);

        mLastNameWrapper = (TextInputLayout) findViewById(R.id.til_last_name);
        mLastNameField = (EditText) findViewById(R.id.et_last_name);

        mEmailWrapper = (TextInputLayout) findViewById(R.id.til_email);
        mEmailField = (EditText) findViewById(R.id.et_email);

        mPhoneWrapper = (TextInputLayout) findViewById(R.id.til_phone);
        mPhoneField = (EditText) findViewById(R.id.et_phone);
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

    /**
     * Updates the phone field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updatePhoneError(boolean show, int errorMessageId) {
        updateErrorDisplay(mPhoneWrapper, show, errorMessageId);
    }
}
