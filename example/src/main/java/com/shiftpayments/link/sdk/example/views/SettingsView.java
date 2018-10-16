package com.shiftpayments.link.sdk.example.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.example.R;
import com.shiftpayments.link.sdk.ui.widgets.HintArrayAdapter;

/**
 * Displays the main View.
 * @author Wijnand
 */
public class SettingsView extends RelativeLayout implements View.OnClickListener {

    /**
     * Callbacks this View will invoke.
     */
    public interface ViewListener {
        /**
         * Called when the "Fill all" button has been pressed.
         */
        void fillAllClickedHandler();

        /**
         * Called when the "Clear all" button has been pressed.
         */
        void clearAllClickedHandler();

        /**
         * Called when the clear token button has been pressed.
         */
        void clearUserTokenClickedHandler();

        /**
         * Called when the clear project key button has been pressed.
         */
        void clearProjectKeyClickedHandler();
    }

    private Button mFillButton;
    private Button mClearButton;
    private ImageButton mClearUserTokenButton;
    private ImageButton mClearProjectKeyButton;

    private EditText mLoanAmountField;
    private Spinner mTimeAtAddressSpinner;
    private Spinner mLoanPurposeSpinner;
    private Spinner mHousingTypeSpinner;
    private Spinner mSalaryFrequencySpinner;
    private Spinner mIncomeTypeSpinner;
    private Spinner mCreditScoreSpinner;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private EditText mPhoneField;
    private EditText mAddressField;
    private EditText mApartmentField;
    private EditText mCityField;
    private EditText mStateField;
    private EditText mZipField;
    private EditText mIncomeField;
    private EditText mBirthday;
    private EditText mSSN;
    private EditText mBearerToken;
    private EditText mProjectKey;
    private EditText mProjectName;
    private EditText mOffersListStyle;
    private Switch mArmedForces;
    private Switch mPaydayLoan;
    private Switch mPOSMode;
    private Switch mSkipDisclaimers;
    private Switch mAddressValidation;
    private RelativeLayout mLoadingOverlay;

    private ViewListener mListener;

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     */
    public SettingsView(Context context) {
        super(context);
    }

    /**
     * @see ScrollView#ScrollView
     * @param context See {@link ScrollView#ScrollView}.
     * @param attrs See {@link ScrollView#ScrollView}.
     */
    public SettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** {@inheritDoc} */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findAllViews();
        setUpListeners();

        if(mBearerToken.getText().toString().isEmpty()) {
            mClearUserTokenButton.setVisibility(GONE);
        }
    }

    /**
     * Finds all references to {@link View}s.
     */
    private void findAllViews() {
        mFillButton = (Button) findViewById(R.id.bttn_fill_all);
        mClearButton = (Button) findViewById(R.id.bttn_clear_all);
        mClearUserTokenButton = (ImageButton) findViewById(R.id.bttn_clear_user_token);
        mClearProjectKeyButton = (ImageButton) findViewById(R.id.bttn_clear_project_key);

        mLoanAmountField = (EditText) findViewById(R.id.et_loan_amount);
        mTimeAtAddressSpinner = (Spinner) findViewById(R.id.sp_time_at_address);
        mLoanPurposeSpinner = (Spinner) findViewById(R.id.sp_loan_purpose);
        mHousingTypeSpinner = (Spinner)  findViewById(R.id.sp_housing_type);
        mIncomeTypeSpinner = (Spinner) findViewById(R.id.sp_employment_status);
        mSalaryFrequencySpinner = (Spinner) findViewById(R.id.sp_salary_frequency);
        mCreditScoreSpinner = (Spinner) findViewById(R.id.sp_credit_score);
        mFirstNameField = (EditText) findViewById(R.id.et_first_name);
        mLastNameField = (EditText) findViewById(R.id.et_last_name);
        mEmailField = (EditText) findViewById(R.id.et_email);
        mPhoneField = (EditText) findViewById(R.id.et_phone);
        mAddressField = (EditText) findViewById(R.id.et_address);
        mApartmentField = (EditText) findViewById(R.id.et_apartment_number);
        mCityField = (EditText) findViewById(R.id.et_city);
        mStateField = (EditText) findViewById(R.id.et_state);
        mZipField = (EditText) findViewById(R.id.et_zip_code);
        mIncomeField = (EditText) findViewById(R.id.et_income);
        mBirthday = (EditText) findViewById(R.id.et_birthday);
        mSSN = (EditText) findViewById(R.id.et_ssn);
        mBearerToken = (EditText) findViewById(R.id.et_user_token);
        mProjectKey = (EditText) findViewById(R.id.et_project_key);
        mProjectName = (EditText) findViewById(R.id.et_project_name);
        mOffersListStyle = (EditText) findViewById(R.id.et_offers_list_style);
        mArmedForces = (Switch) findViewById(R.id.sw_armed_forces);
        mPaydayLoan = (Switch) findViewById(R.id.sw_payday_loan);
        mPOSMode = (Switch) findViewById(R.id.sw_pos_mode) ;
        mSkipDisclaimers = (Switch) findViewById(R.id.sw_skip_link_disclaimers);
        mAddressValidation = (Switch) findViewById(R.id.sw_address_validation);
        mLoadingOverlay = (RelativeLayout) findViewById(R.id.rl_loading_overlay);
    }

    /**
     * Sets up all relevant callback listeners.
     */
    private void setUpListeners() {
        mFillButton.setOnClickListener(this);
        mClearButton.setOnClickListener(this);
        mClearUserTokenButton.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        if (mListener == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.bttn_fill_all:
                mListener.fillAllClickedHandler();
                break;
            case R.id.bttn_clear_all:
                mListener.clearAllClickedHandler();
                break;
            case R.id.bttn_clear_user_token:
                mListener.clearUserTokenClickedHandler();
                break;
            case R.id.bttn_clear_project_key:
                mListener.clearProjectKeyClickedHandler();
                break;
            default:
                // Do nothing.
                break;
        }
    }

    /**
     * Stores a new {@link ViewListener}.
     * @param listener The new listener.
     */
    public void setViewListener(ViewListener listener) {
        mListener = listener;
    }

    /**
     * Stores a new {@link HintArrayAdapter} for the {@link Spinner} to use.
     * @param adapter New {@link HintArrayAdapter}.
     */
    public void setPurposeAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mLoanPurposeSpinner.setAdapter(adapter);
    }

    public void setHousingAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mHousingTypeSpinner.setAdapter(adapter);
    }

    public void setSalaryAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mSalaryFrequencySpinner.setAdapter(adapter);
    }

    public void setIncomeTypeAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mIncomeTypeSpinner.setAdapter(adapter);
    }

    public void setCreditScoreAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mCreditScoreSpinner.setAdapter(adapter);
    }

    public void setTimeAtAddressAdapter(HintArrayAdapter<IdDescriptionPairDisplayVo> adapter) {
        mTimeAtAddressSpinner.setAdapter(adapter);
    }

    /**
     * Changes the loading overlay visibility.
     * @param show Whether the loading overlay should be shown.
     */
    public void showLoading(boolean show) {
        if (show) {
            mLoadingOverlay.setVisibility(VISIBLE);
        } else {
            mLoadingOverlay.setVisibility(GONE);
        }
    }

    /**
     * @return Loan amount.
     */
    public String getLoanAmount() {
        return mLoanAmountField.getText().toString();
    }

    /**
     * Shows a new loan amount.
     * @param amount New loan amount.
     */
    public void setLoanAmount(String amount) {
        mLoanAmountField.setText(amount);
    }

    /**
     * @return Loan purpose.
     */
    public IdDescriptionPairDisplayVo getLoanPurpose() {
        return (IdDescriptionPairDisplayVo) mLoanPurposeSpinner.getSelectedItem();
    }

    /**
     * Resets the loan purpose Spinner.
     */
    public void setLoanPurpose(int index) {
        mLoanPurposeSpinner.setSelection(index);
    }

    /**
     * @return Housing type.
     */
    public IdDescriptionPairDisplayVo getHousingType() {
        return (IdDescriptionPairDisplayVo) mHousingTypeSpinner.getSelectedItem();
    }

    /**
     * Resets the housing type Spinner.
     */
    public void setHousingType(int index) {
        mHousingTypeSpinner.setSelection(index);
    }

    /**
     * @return Salary frequency.
     */
    public IdDescriptionPairDisplayVo getSalaryFrequency() {
        return (IdDescriptionPairDisplayVo) mSalaryFrequencySpinner.getSelectedItem();
    }

    /**
     * Resets the salary frequency Spinner.
     */
    public void setSalaryFrequency(int index) {
        mSalaryFrequencySpinner.setSelection(index);
    }

    /**
     * @return Income type.
     */
    public IdDescriptionPairDisplayVo getIncomeType() {
        return (IdDescriptionPairDisplayVo) mIncomeTypeSpinner.getSelectedItem();
    }

    /**
     * Resets the income type Spinner.
     */
    public void setIncomeType(int index) {
        mIncomeTypeSpinner.setSelection(index);
    }

    /**
     * @return Credit score.
     */
    public IdDescriptionPairDisplayVo getCreditScore() {
        return (IdDescriptionPairDisplayVo) mCreditScoreSpinner.getSelectedItem();
    }

    /**
     * Resets the credit score Spinner.
     */
    public void setCreditScore(int index) {
        mCreditScoreSpinner.setSelection(index);
    }

    /**
     * @return First name.
     */
    public String getFirstName() {
        return mFirstNameField.getText().toString();
    }

    /**
     * Shows a new first name.
     * @param name New first name.
     */
    public void setFirstName(String name) {
        mFirstNameField.setText(name);
    }

    /**
     * @return Last name.
     */
    public String getLastName() {
        return mLastNameField.getText().toString();
    }

    /**
     * Shows a new last name.
     * @param name New last name.
     */
    public void setLastName(String name) {
        mLastNameField.setText(name);
    }

    /**
     * @return Email address.
     */
    public String getEmail() {
        return mEmailField.getText().toString();
    }

    /**
     * Shows a new email address.
     * @param email New email.
     */
    public void setEmail(String email) {
        mEmailField.setText(email);
    }

    /**
     * @return Phone number.
     */
    public String getPhoneNumber() {
        return mPhoneField.getText().toString();
    }

    /**
     * Shows a new phone number.
     * @param phone New phone number.
     */
    public void setPhoneNumber(String phone) {
        mPhoneField.setText(phone);
    }

    /**
     * @return Address.
     */
    public String getAddress() {
        return mAddressField.getText().toString();
    }

    /**
     * Shows a new address.
     * @param address New address.
     */
    public void setAddress(String address) {
        mAddressField.setText(address);
    }

    /**
     * @return Apartment number.
     */
    public String getApartmentNumber() {
        return mApartmentField.getText().toString();
    }

    /**
     * Shows a new apartment number.
     * @param number New apartment number.
     */
    public void setApartmentNumber(String number) {
        mApartmentField.setText(number);
    }

    /**
     * @return City.
     */
    public String getCity() {
        return mCityField.getText().toString();
    }

    /**
     * Shows a new city.
     * @param city New city.
     */
    public void setCity(String city) {
        mCityField.setText(city);
    }

    /**
     * @return State.
     */
    public String getState() {
        return mStateField.getText().toString();
    }

    /**
     * Shows a new state.
     * @param state New state.
     */
    public void setState(String state) {
        mStateField.setText(state);
    }

    /**
     * @return Zip code.
     */
    public String getZipCode() {
        return mZipField.getText().toString();
    }

    /**
     * Shows a new ZIP code.
     * @param zip New ZIP code.
     */
    public void setZipCode(String zip) {
        mZipField.setText(zip);
    }

    /**
     * @return Income.
     */
    public String getIncome() {
        return mIncomeField.getText().toString();
    }

    /**
     * Shows a new income.
     * @param income New income.
     */
    public void setIncome(String income) {
        mIncomeField.setText(income);
    }

    /**
     * Shows the stored user token.
     * @param token User token.
     */
    public void setUserToken(String token) {
        if(token!=null && !token.isEmpty()) {
            mBearerToken.setText(token.substring(0,10) + "..." +
                    token.substring(token.length()-10, token.length()));
            mClearUserTokenButton.setVisibility(VISIBLE);
        }
        else {
            mBearerToken.setText("");
            mClearUserTokenButton.setVisibility(GONE);
        }
    }

    /**
     * Shows the stored team key.
     * @param projectKey User token.
     */
    public void setProjectKey(String projectKey) {
        if(projectKey!=null && !projectKey.isEmpty()) {
            mProjectKey.setText(projectKey.substring(0,10) + "..." +
                    projectKey.substring(projectKey.length()-10, projectKey.length()));
            mClearProjectKeyButton.setVisibility(VISIBLE);
        }
        else {
            mProjectKey.setText("");
            mClearProjectKeyButton.setVisibility(GONE);
        }
    }

    public String getProjectKey() {
        return mProjectKey.getText().toString();
    }

    /**
     * @return Birthday.
     */
    public String getBirthday() {
        return mBirthday.getText().toString();
    }

    /**
     * Shows a new birthday.
     * @param birthday New birthday.
     */
    public void setBirthday(String birthday) {
        mBirthday.setText(birthday);
    }

    /**
     * @return Social security number.
     */
    public String getSSN() {
        return mSSN.getText().toString();
    }

    /**
     * Shows a new social security number.
     * @param ssn New Social security number.
     */
    public void setSSN(String ssn) {
        mSSN.setText(ssn);
    }

    /**
     * Shows the offers list style.
     * @param style Offers list style.
     */
    public void setOffersListStyle(String style) {
        mOffersListStyle.setText(style);
    }

    /**
     * Shows the POS mode.
     * @param enabled Is POS mode enabled.
     */
    public void setPOSMode(boolean enabled) {
        mPOSMode.setChecked(enabled);
    }

    /**
     * Shows if link disclaimers should be skipped.
     * @param enabled Is skip disclaimers mode enabled.
     */
    public void setSkipDisclaimers(boolean enabled) {
        mSkipDisclaimers.setChecked(enabled);
    }

    /**
     * Shows if strict address validation is enabled.
     * @param enabled Is strict address validation enabled.
     */
    public void setStrictAddressValidation(boolean enabled) {
        mAddressValidation.setChecked(enabled);
    }

    /**
     * Shows the project name.
     * @param project Project name
     */
    public void setProjectName(String project) {
        mProjectName.setText(project);
    }

    public boolean getArmedForces() {
        return mArmedForces.isChecked();
    }

    public void setArmedForces(boolean isMember) {
        mArmedForces.setChecked(isMember);
    }

    public boolean getPaydayLoan() {
        return mPaydayLoan.isChecked();
    }

    public void setPaydayLoan(boolean hasUsedPaydayLoan) {
        mPaydayLoan.setChecked(hasUsedPaydayLoan);
    }

    public IdDescriptionPairDisplayVo getTimeAtAddress() {
        return (IdDescriptionPairDisplayVo) mTimeAtAddressSpinner.getSelectedItem();
    }

    public void setTimeAtAddress(int index) {
        mTimeAtAddressSpinner.setSelection(index);
    }

    public void showPersonalName() {
        mFirstNameField.setVisibility(VISIBLE);
        mLastNameField.setVisibility(VISIBLE);
    }

    public void showPhoneNumber() {
        mPhoneField.setVisibility(VISIBLE);
    }

    public void showEmail() {
        mEmailField.setVisibility(VISIBLE);
    }

    public void showBirthday() {
        mBirthday.setVisibility(VISIBLE);
    }

    public void showSSN() {
        mSSN.setVisibility(VISIBLE);
    }

    public void showAddress() {
        mAddressField.setVisibility(VISIBLE);
        mZipField.setVisibility(VISIBLE);
        mCityField.setVisibility(VISIBLE);
        mStateField.setVisibility(VISIBLE);
        mApartmentField.setVisibility(VISIBLE);
    }

    public void showHousing() {
        mHousingTypeSpinner.setVisibility(VISIBLE);
    }

    public void showEmployment() {
        mIncomeTypeSpinner.setVisibility(VISIBLE);
        mSalaryFrequencySpinner.setVisibility(VISIBLE);
    }

    public void showIncome() {
        mIncomeField.setVisibility(VISIBLE);
    }

    public void showCreditScore() {
        mCreditScoreSpinner.setVisibility(VISIBLE);
    }

    public void showPayDayLoan() {
        mPaydayLoan.setVisibility(VISIBLE);
    }

    public void showMemberOfArmedForces() {
        mArmedForces.setVisibility(VISIBLE);
    }

    public void showTimeAtAddress() {
        mTimeAtAddressSpinner.setVisibility(VISIBLE);
    }

    public boolean isArmedForcesVisible() {
        return mArmedForces.getVisibility() == VISIBLE;
    }

    public boolean isPayDayLoanVisible() {
        return mPaydayLoan.getVisibility() == VISIBLE;
    }
}
