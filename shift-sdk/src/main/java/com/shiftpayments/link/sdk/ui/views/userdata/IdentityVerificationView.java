package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.LoadingView;
import com.shiftpayments.link.sdk.ui.views.ViewWithIndeterminateLoading;
import com.shiftpayments.link.sdk.ui.views.ViewWithToolbar;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

/**
 * Displays the user details screen.
 * @author Wijnand
 */
public class IdentityVerificationView
        extends UserDataView<IdentityVerificationView.ViewListener>
        implements View.OnClickListener, ViewWithToolbar, ViewWithIndeterminateLoading, AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View row, int pos, long l) {

        int id = parent.getId();
        if (id == R.id.sp_document_type) {
            mListener.documentTypeClickHandler((String) parent.getItemAtPosition(pos));
        }
        else if(id == R.id.sp_citizenship) {
            mListener.citizenshipClickHandler((String) parent.getItemAtPosition(pos));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void ssnCheckBoxClickHandler();
        void monthClickHandler();
        void citizenshipClickHandler(String country);
        void documentTypeClickHandler(String documentType);
    }

    private EditText mBirthdayMonth;
    private EditText mBirthdayDay;
    private EditText mBirthdayYear;
    private TextView mBirthdateErrorView;
    private TextView mBirthdateHint;
    private TextInputLayout mDocumentNumberWrapper;

    private EditText mDocumentNumberField;
    private CheckBox mSocialSecurityAvailableCheck;
    private TextView mSocialSecurityAvailableField;

    private TextView mCitizenshipLabel;
    private Spinner mCitizenshipSpinner;

    private Spinner mDocumentTypeSpinner;

    private LoadingView mLoadingView;

    private ProgressBar mProgressBar;
    private TextView mNextButton;
    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     */
    public IdentityVerificationView(Context context) {
        super(context);
    }

    /**
     * @see UserDataView#UserDataView
     * @param context See {@link UserDataView#UserDataView}.
     * @param attrs See {@link UserDataView#UserDataView}.
     */
    public IdentityVerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setColors() {
        super.setColors();

        int primaryColor = UIStorage.getInstance().getUiPrimaryColor();
        ((AppCompatCheckBox) mSocialSecurityAvailableCheck).setSupportButtonTintList(
                ColorStateList.valueOf(primaryColor));
        mNextButton.setBackgroundColor(primaryColor);

        mBirthdateHint.setTextColor(UIStorage.getInstance().getTextPrimaryColor());
        Integer textSecondaryColor = UIStorage.getInstance().getTextSecondaryColor();
        Integer textTertiaryColor = UIStorage.getInstance().getTextTertiaryColor();
        mBirthdayMonth.setTextColor(textSecondaryColor);
        mBirthdayMonth.setHintTextColor(textTertiaryColor);
        mBirthdayDay.setTextColor(textSecondaryColor);
        mBirthdayDay.setHintTextColor(textTertiaryColor);
        mBirthdayYear.setTextColor(textSecondaryColor);
        mBirthdayYear.setHintTextColor(textTertiaryColor);
        mDocumentNumberField.setTextColor(textSecondaryColor);
        mDocumentNumberField.setHintTextColor(textTertiaryColor);
        UIStorage.getInstance().setCursorColor(mBirthdayDay);
        UIStorage.getInstance().setCursorColor(mBirthdayYear);
        UIStorage.getInstance().setCursorColor(mDocumentNumberField);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mBirthdayMonth = findViewById(R.id.et_birthday_month);
        mBirthdayDay = findViewById(R.id.et_birthday_day);
        mBirthdayYear = findViewById(R.id.et_birthday_year);
        mBirthdateErrorView = findViewById(R.id.tv_birthdate_error);
        mBirthdateHint = findViewById(R.id.tv_birthdate_label);

        mDocumentNumberWrapper = findViewById(R.id.til_document_number);
        mDocumentNumberField = findViewById(R.id.et_social_security);
        mSocialSecurityAvailableCheck = findViewById(R.id.cb_ssn_itin_not_available);
        mSocialSecurityAvailableField = findViewById(R.id.tv_ssn_itin_not_available);

        // remove hint from `TextInputLayout`
        mDocumentNumberWrapper.setHint(null);
        // set the hint back on the `EditText`
        mDocumentNumberField.setHint(R.string.id_verification_document_number_hint);

        mCitizenshipLabel = findViewById(R.id.tv_citizenship_label);
        mCitizenshipSpinner = findViewById(R.id.sp_citizenship);

        mDocumentTypeSpinner = findViewById(R.id.sp_document_type);

        mNextButton = findViewById(R.id.tv_next_bttn);

        mLoadingView = findViewById(R.id.rl_loading_overlay);
        mProgressBar = findViewById(R.id.pb_progress);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mDocumentNumberField.setOnClickListener(this);
        mSocialSecurityAvailableCheck.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mBirthdayMonth.setOnClickListener(this);
        mCitizenshipSpinner.setOnItemSelectedListener(this);
        mDocumentTypeSpinner.setOnItemSelectedListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        int id = view.getId();
        if (id == R.id.cb_ssn_itin_not_available) {
            mListener.ssnCheckBoxClickHandler();
        }
        else if (id == R.id.tv_next_bttn) {
            mListener.nextClickHandler();
        }
        else if (id == R.id.et_birthday_month) {
            mListener.monthClickHandler();
        }
    }

    /**
     * @return The selected month.
     */
    public String getBirthdayMonth() {
        return mBirthdayMonth.getText().toString();
    }

    /**
     * Sets a new month.
     * @param month Month.
     */
    public void setBirthdayMonth(String month) {
        mBirthdayMonth.setText(month);
    }

    /**
     * Sets a new color resource for the progress bar to use.
     * @param colorResourceId Color resource ID.
     */
    public void setProgressColor(int colorResourceId) {
        if (mProgressBar == null || mProgressBar.getIndeterminateDrawable() == null) {
            return;
        }

        mProgressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(colorResourceId), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    /**
     * @return ID document number.
     */
    public String getDocumentNumber() {
        return mDocumentNumberField.getText().toString();
    }

    public void setBirthdayDay(String day) {
        mBirthdayDay.setText(day);
    }

    public String getBirthdayDay() {
        return mBirthdayDay.getText().toString();
    }

    public void setBirthdayYear(String year) {
        mBirthdayYear.setText(year);
    }

    public String getBirthdayYear() {
        return mBirthdayYear.getText().toString();
    }

    /**
     * Shows the user's ID document number
     * @param documentNumber ID document number
     */
    public void setDocumentNumber(String documentNumber) {
        mDocumentNumberField.setText(documentNumber);
    }

    public void showSSN(boolean show) {
        if(show) {
            mDocumentNumberField.setVisibility(VISIBLE);
        }
        else {
            mDocumentNumberField.setVisibility(GONE);
        }
    }

    public void showSSNNotAvailableCheckbox(boolean show) {
        if(show) {
            mSocialSecurityAvailableCheck.setVisibility(VISIBLE);
            mSocialSecurityAvailableField.setVisibility(VISIBLE);
        }
        else {
            mSocialSecurityAvailableCheck.setVisibility(GONE);
            mSocialSecurityAvailableField.setVisibility(GONE);
        }
    }

    public boolean isSSNCheckboxChecked() {
        return mSocialSecurityAvailableCheck.isChecked();
    }

    public void enableSSNField(boolean enabled) {
        mDocumentNumberField.setEnabled(enabled);
    }

    /**
     * Updates the birthday field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateBirthdayError(boolean show, int errorMessageId) {
        if(show) {
            String error = getResources().getString(errorMessageId);
            mBirthdateErrorView.setText(error);
            mBirthdateErrorView.setVisibility(VISIBLE);
        }
        else {
            mBirthdateErrorView.setVisibility(GONE);
        }
    }

    /**
     * Updates the SSN field error display.
     * @param show Whether the error should be shown.
     * @param errorMessageId Error message resource ID.
     */
    public void updateDocumentNumberError(boolean show, int errorMessageId) {
        updateErrorDisplay(mDocumentNumberWrapper, show, errorMessageId);
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void setButtonText(String buttonText) {
        mNextButton.setText(buttonText);
    }

    public void setCitizenshipSpinnerAdapter(ArrayAdapter<String> adapter) {
        mCitizenshipSpinner.setAdapter(adapter);
    }

    public void setDocumentTypeSpinnerAdapter(ArrayAdapter<String> adapter) {
        mDocumentTypeSpinner.setAdapter(adapter);
    }

    public String getCitizenship() {
        return (String) mCitizenshipSpinner.getSelectedItem();
    }

    public void setCitizenship(int selection) {
        mCitizenshipSpinner.setSelection(selection);
    }

    public void showBirthday(boolean show) {
        if(show) {
            mBirthdayMonth.setVisibility(VISIBLE);
            mBirthdayDay.setVisibility(VISIBLE);
            mBirthdayYear.setVisibility(VISIBLE);
        }
        else {
            mBirthdayMonth.setVisibility(GONE);
            mBirthdayDay.setVisibility(GONE);
            mBirthdayYear.setVisibility(GONE);
        }
    }

    public void showCitizenshipSpinner(boolean show) {
        if(show) {
            mCitizenshipSpinner.setVisibility(VISIBLE);
            mCitizenshipLabel.setVisibility(VISIBLE);
        }
        else {
            mCitizenshipSpinner.setVisibility(GONE);
            mCitizenshipLabel.setVisibility(GONE);
        }
    }
}
