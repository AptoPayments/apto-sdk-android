package com.shiftpayments.link.sdk.ui.views.userdata;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.View;
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
import com.shiftpayments.link.sdk.ui.widgets.SsnEditText;
import com.shiftpayments.link.sdk.ui.widgets.steppers.StepperListener;

import java.util.Arrays;

/**
 * Displays the user details screen.
 * @author Wijnand
 */
public class IdentityVerificationView
        extends UserDataView<IdentityVerificationView.ViewListener>
        implements View.OnClickListener, ViewWithToolbar, ViewWithIndeterminateLoading {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener extends StepperListener, NextButtonListener {
        void ssnCheckBoxClickHandler();
    }

    private Spinner mBirthdayMonthSpinner;
    private EditText mBirthdayDay;
    private EditText mBirthdayYear;
    private TextView mBirthdateErrorView;
    private TextInputLayout mSocialSecurityWrapper;

    private SsnEditText mSocialSecurityField;
    private CheckBox mSocialSecurityAvailableCheck;
    private TextView mSocialSecurityAvailableField;
    private TextView mDisclaimer;

    private TextView mDisclaimersHeader;
    private TextView mDisclaimersField;
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

        int primaryColor = UIStorage.getInstance().getPrimaryColor();
        ((AppCompatCheckBox) mSocialSecurityAvailableCheck).setSupportButtonTintList(
                ColorStateList.valueOf(primaryColor));
        mNextButton.setBackgroundColor(primaryColor);
    }

    /** {@inheritDoc} */
    @Override
    protected void findAllViews() {
        super.findAllViews();
        mBirthdayMonthSpinner = findViewById(R.id.sp_birthday_month);
        mBirthdayDay = findViewById(R.id.et_birthday_day);
        mBirthdayYear = findViewById(R.id.et_birthday_year);
        mBirthdateErrorView = findViewById(R.id.tv_birthdate_error);

        mSocialSecurityWrapper = findViewById(R.id.til_social_security);
        mSocialSecurityField = findViewById(R.id.et_social_security);
        mSocialSecurityAvailableCheck = findViewById(R.id.cb_ssn_itin_not_available);
        mSocialSecurityAvailableField = findViewById(R.id.tv_ssn_itin_not_available);

        mDisclaimer = findViewById(R.id.tv_security);
        mDisclaimersHeader = findViewById(R.id.tv_disclaimers_header);
        mDisclaimersField = findViewById(R.id.tv_disclaimers_body);
        mDisclaimersField.setMovementMethod(LinkMovementMethod.getInstance());

        mNextButton = findViewById(R.id.tv_next_bttn);

        mLoadingView = findViewById(R.id.rl_loading_overlay);
        mProgressBar = findViewById(R.id.pb_progress);
    }

    /** {@inheritDoc} */
    @Override
    protected void setupListeners() {
        super.setupListeners();
        mSocialSecurityField.setOnClickListener(this);
        mSocialSecurityAvailableCheck.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
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
    }

    public void setBirthdayMonthAdapter(ArrayAdapter<String> adapter) {
        mBirthdayMonthSpinner.setAdapter(adapter);
    }

    /**
     * @return The selected month.
     */
    public int getBirthdayMonth() {
        return mBirthdayMonthSpinner.getSelectedItemPosition();
    }

    /**
     * Sets a new month.
     * @param index Month index.
     */
    public void setBirthdayMonth(int index) {
        mBirthdayMonthSpinner.setSelection(index);
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
     * @return Social security number.
     */
    public String getSocialSecurityNumber() {
        return mSocialSecurityField.getSsn();
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
     * Shows the user's social security number with hyphens.
     * @param ssn social security number.
     */
    public void setSSN(String ssn) {
        ssn = new StringBuilder(ssn).insert(3, "-").toString();
        ssn = new StringBuilder(ssn).insert(6, "-").toString();
        mSocialSecurityField.setText(ssn);
    }

    private String getMaskedSSN() {
        final char DOT = '\u2022';
        char[] mask = new char[getResources().getInteger(R.integer.ssn_length)];
        Arrays.fill(mask, DOT);
        return new String(mask);
    }

    public void setMaskedSSN() {
        setSSN(getMaskedSSN());
    }

    public boolean isSSNMasked() {
        return getMaskedSSN().equals(getSocialSecurityNumber());
    }

    public void showSSN(boolean show) {
        if(show) {
            mSocialSecurityField.setVisibility(VISIBLE);
        }
        else {
            mSocialSecurityField.setVisibility(GONE);
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
        mSocialSecurityField.setEnabled(enabled);
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
    public void updateSocialSecurityError(boolean show, int errorMessageId) {
        updateErrorDisplay(mSocialSecurityWrapper, show, errorMessageId);
    }

    public void setDisclaimers(String disclaimers) {
        mDisclaimersHeader.setVisibility(VISIBLE);
        mDisclaimersField.setText(Html.fromHtml(disclaimers));
    }

    @Override
    public LoadingView getLoadingView() {
        return mLoadingView;
    }

    public void setButtonText(String buttonText) {
        mNextButton.setText(buttonText);
    }

    public void showDisclaimers(boolean show) {
        if(show) {
            mDisclaimer.setVisibility(VISIBLE);
            mDisclaimersHeader.setVisibility(VISIBLE);
            mDisclaimersField.setVisibility(VISIBLE);
        }
        else {
            mDisclaimer.setVisibility(GONE);
            mDisclaimersHeader.setVisibility(GONE);
            mDisclaimersField.setVisibility(GONE);
        }
    }

    public void showBirthday(boolean show) {
        if(show) {
            mBirthdayMonthSpinner.setVisibility(VISIBLE);
            mBirthdayDay.setVisibility(VISIBLE);
            mBirthdayYear.setVisibility(VISIBLE);
        }
        else {
            mBirthdayMonthSpinner.setVisibility(GONE);
            mBirthdayDay.setVisibility(GONE);
            mBirthdayYear.setVisibility(GONE);
        }
    }
}
