package com.shiftpayments.link.sdk.example.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.IdDescriptionPairDisplayVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.ArmedForces;
import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.CreditScore;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.Housing;
import com.shiftpayments.link.sdk.api.vos.datapoints.Income;
import com.shiftpayments.link.sdk.api.vos.datapoints.IncomeSource;
import com.shiftpayments.link.sdk.api.vos.datapoints.PaydayLoan;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.api.vos.datapoints.PhoneNumberVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.SSN;
import com.shiftpayments.link.sdk.api.vos.datapoints.TimeAtAddress;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.CreditScoreVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.HousingTypeVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.IncomeTypeVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LoanPurposeVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.LoanPurposesResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.SalaryFrequencyVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.TimeAtAddressVo;
import com.shiftpayments.link.sdk.example.KeysStorage;
import com.shiftpayments.link.sdk.example.R;
import com.shiftpayments.link.sdk.example.views.SettingsView;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.vos.LoanDataVo;
import com.shiftpayments.link.sdk.ui.widgets.HintArrayAdapter;

import java.lang.ref.WeakReference;

/**
 * Settings display.
 * @author Adrian
 */
public class SettingsActivity extends AppCompatActivity implements SettingsView.ViewListener {

    private SettingsView mView;
    private ConfigResponseVo mProjectConfig;

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (SettingsView) View.inflate(this, R.layout.act_settings, null);
        mView.setViewListener(this);
        setContentView(mView);
        mView.showLoading(true);

        if(SharedPreferencesStorage.getUserToken(this, false) != null) {
            mView.setUserToken(UserStorage.getInstance().getBearerToken());
        }

        mView.setProjectKey(KeysStorage.getProjectToken(this, ""));
        mView.setTeamKey(KeysStorage.getDeveloperKey(this, ""));

        setUpToolbar();
        new LoadConfigTask().execute();
    }

    /**
     * @param source String source to parse to an integer.
     * @return Parsed integer.
     */
    private int parseIntSafely(String source) {
        int result;

        try {
            result = Integer.parseInt(source);
        } catch (NumberFormatException nfe) {
            result = 0;
        }

        return result;
    }

    /**
     * @return Pre-fill data for the Shift Line SDK to use.
     */
    private LoanDataVo createLoanData() {
        LoanDataVo loanData = new LoanDataVo();
        if (hasValue(mView.getLoanAmount())) {
            loanData.loanAmount = parseIntSafely(mView.getLoanAmount());
        }
        if (mView.getLoanPurpose() != null && mView.getLoanPurpose().getKey()!=-1) {
            loanData.loanPurpose = mView.getLoanPurpose();
        }
        return loanData;
    }

    /**
     * @return Pre-fill data for the Shift Line SDK to use.
     */
    private DataPointList createStartData() {
        DataPointList data = new DataPointList();
        boolean dataSet = false;

        if (hasValue(mView.getFirstName()) && hasValue(mView.getLastName())) {
            data.add(new PersonalName(mView.getFirstName(), mView.getLastName(), false, false));
            dataSet = true;
        }
        if (hasValue(mView.getEmail())) {
            data.add(new Email(mView.getEmail(), false, false));
            dataSet = true;
        }
        if (hasValue(mView.getPhoneNumber())) {
            data.add(new PhoneNumberVo(mView.getPhoneNumber(), false, false));
            dataSet = true;
        }
        String address = "";
        if (hasValue(mView.getAddress())) {
            address = mView.getAddress();
            dataSet = true;
        }
        String apartment = "";
        if (hasValue(mView.getApartmentNumber())) {
            apartment = mView.getApartmentNumber();
            dataSet = true;
        }
        String city = "";
        if (hasValue(mView.getCity())) {
            city = mView.getCity();
            dataSet = true;
        }
        String state = "";
        if (hasValue(mView.getState())) {
            state = mView.getState();
            dataSet = true;
        }
        String zip = "";
        if (hasValue(mView.getZipCode())) {
            zip = mView.getZipCode();
            dataSet = true;
        }
        if(!address.isEmpty() || !apartment.isEmpty() || !city.isEmpty() || !state.isEmpty() || zip.isEmpty()) {
            Address addressDataPoint = new Address(address, apartment, "US", city, state, zip, false, false);
            data.add(addressDataPoint);
        }
        if (hasValue(mView.getIncome())) {
            data.add(new Income(-1, parseIntSafely(mView.getIncome()), false, false));
            dataSet = true;
        }
        if (mView.getHousingType() != null && mView.getHousingType().getKey() != -1) {
            data.add(new Housing(mView.getHousingType().getKey(),false, false));
            dataSet = true;
        }
        if ((mView.getIncomeType() != null && mView.getIncomeType().getKey() != -1) &&
                (mView.getSalaryFrequency() != null && mView.getSalaryFrequency().getKey() != -1)) {
            data.add(new IncomeSource(mView.getIncomeType().getKey(), mView.getSalaryFrequency().getKey(), false, false));
            dataSet = true;
        }
        if (mView.getCreditScore() != null && mView.getCreditScore().getKey() != -1) {
            data.add(new CreditScore(mView.getCreditScore().getKey(), false, false));
            dataSet = true;
        }
        if (hasValue(mView.getBirthday())) {
            data.add(new Birthdate(mView.getBirthday(), false, false));
            dataSet = true;
        }
        if (hasValue(mView.getSSN())) {
            data.add(new SSN(mView.getSSN(), false, false));
            dataSet = true;
        }
        if(mView.isArmedForcesVisible()) {
            data.add(new ArmedForces(mView.getArmedForces(), false, false));
        }
        if(mView.isPayDayLoanVisible()) {
            data.add(new PaydayLoan(mView.getPaydayLoan(), false, false));
        }
        if ((mView.getTimeAtAddress() != null && mView.getTimeAtAddress().getKey() != -1)) {
            data.add(new TimeAtAddress(mView.getTimeAtAddress().getKey(), false, false));
            dataSet = true;
        }
        if (!dataSet) {
            data = null;
        }

        return data;
    }

    /**
     * @param fieldValue Field value to check.
     * @return Whether the field has a value set.
     */
    private boolean hasValue(String fieldValue) {
        return !TextUtils.isEmpty(fieldValue);
    }

    private void setUpToolbar() {
        Toolbar settingsToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(settingsToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        MainActivity.SHARED_USER_DATA.put(MainActivity.USER_DATA_KEY, new WeakReference<>(createStartData()));
        MainActivity.SHARED_LOAN_DATA.put(MainActivity.LOAN_DATA_KEY, new WeakReference<>(createLoanData()));
        if(!mView.getProjectKey().isEmpty()) {
            KeysStorage.storeProjectKey(this, mView.getProjectKey());
        }
        if(!mView.getTeamKey().isEmpty()) {
            KeysStorage.storeTeamKey(this, mView.getTeamKey());
        }

        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public void fillAllClickedHandler() {
        mView.setLoanAmount(getString(R.string.data_michael_loan_amount));
        mView.setLoanPurpose(Integer.parseInt(getString(R.string.data_michael_loan_purpose)));
        mView.setFirstName(getString(R.string.data_michael_first_name));
        mView.setLastName(getString(R.string.data_michael_last_name));
        mView.setEmail(getString(R.string.data_michael_email));
        mView.setPhoneNumber(getString(R.string.data_michael_phone));
        mView.setHousingType(Integer.parseInt(getString(R.string.data_michael_housing_type)));
        mView.setAddress(getString(R.string.data_michael_address));
        mView.setCity(getString(R.string.data_michael_city));
        mView.setState(getString(R.string.data_michael_state));
        mView.setZipCode(getString(R.string.data_michael_zip));
        mView.setIncome(getString(R.string.data_michael_income));
        mView.setBirthday(getString(R.string.data_michael_birthday));
        mView.setSSN(getString(R.string.data_michael_ssn));
        mView.setSalaryFrequency(Integer.parseInt(getString(R.string.data_michael_salary_frequency)));
        mView.setIncomeType(Integer.parseInt(getString(R.string.data_michael_income_type)));
        mView.setCreditScore(Integer.parseInt(getString(R.string.data_michael_credit_score)));
        mView.setTimeAtAddress(Integer.parseInt(getString(R.string.data_michael_time_at_address)));
    }

    /** {@inheritDoc} */
    @Override
    public void clearAllClickedHandler() {
        mView.setLoanAmount("");
        mView.setLoanPurpose(0);
        mView.setFirstName("");
        mView.setLastName("");
        mView.setEmail("");
        mView.setPhoneNumber("");
        mView.setHousingType(0);
        mView.setAddress("");
        mView.setApartmentNumber("");
        mView.setCity("");
        mView.setState("");
        mView.setZipCode("");
        mView.setIncome("");
        mView.setBirthday("");
        mView.setSSN("");
        mView.setSalaryFrequency(0);
        mView.setIncomeType(0);
        mView.setCreditScore(0);
        mView.setTimeAtAddress(0);
    }

    @Override
    public void clearUserTokenClickedHandler() {
        mView.setUserToken("");
        ShiftPlatform.clearUserToken(this);
        MainActivity.SHARED_USER_DATA.put(MainActivity.USER_DATA_KEY, new WeakReference<>(null));
    }

    @Override
    public void clearTeamKeyClickedHandler() {
        mView.setTeamKey("");
        KeysStorage.storeTeamKey(this, "");
    }

    @Override
    public void clearProjectKeyClickedHandler() {
        mView.setProjectKey("");
        KeysStorage.storeProjectKey(this, "");
    }

    public void loanPurposesListRetrieved(LoanPurposesResponseVo loanPurposesList) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        runOnUiThread(() -> {
            IdDescriptionPairDisplayVo hint =
                    new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_loan_purpose));
            adapter.add(hint);

            if (loanPurposesList != null && loanPurposesList.data != null) {
                for (LoanPurposeVo purpose : loanPurposesList.data) {
                    adapter.add(new IdDescriptionPairDisplayVo(purpose.loan_purpose_id, purpose.description));
                }
            }
            mView.setPurposeAdapter(adapter);
        });
    }

    private void displayConfigElements() {
        mProjectConfig = UIStorage.getInstance().getContextConfig();
        if(mProjectConfig==null) {
            return;
        }

        mView.setOffersListStyle(ConfigStorage.getInstance().getOffersListStyle().toString());
        mView.setPOSMode(ConfigStorage.getInstance().getPOSMode());
        mView.setSkipDisclaimers(ConfigStorage.getInstance().getSkipLinkDisclaimer());
        mView.setStrictAddressValidation(ConfigStorage.getInstance().isStrictAddressValidationEnabled());
        mView.setProjectName(mProjectConfig.name);
        mView.setTeamName(UIStorage.getInstance().getTeamConfig().name);
    }

    private void displayHousingType() {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_housing_type));
        adapter.add(hint);

        HousingTypeVo[] typesList = mProjectConfig.housingTypeOpts.data;
        if (typesList != null) {
            for (HousingTypeVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.housing_type_id, type.description));
            }
        }

        mView.setHousingAdapter(adapter);
    }

    private void displaySalaryFrequencies() {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_salary_frequency));
        adapter.add(hint);

        SalaryFrequencyVo[] typesList = mProjectConfig.salaryFrequencyOpts.data;
        if (typesList != null) {
            for (SalaryFrequencyVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.salary_frequency_id, type.description));
            }
        }

        mView.setSalaryAdapter(adapter);
    }

    private void displayEmploymentStatus() {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_income_type));
        adapter.add(hint);

        IncomeTypeVo[] typesList = mProjectConfig.incomeTypeOpts.data;
        if (typesList != null) {
            for (IncomeTypeVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.income_type_id, type.description));
            }
        }

        mView.setIncomeTypeAdapter(adapter);
    }

    private void displayCreditScore() {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_credit_score));
        adapter.add(hint);

        CreditScoreVo[] typesList = mProjectConfig.creditScoreOpts.data;
        if (typesList != null) {
            for (CreditScoreVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.creditScoreId, type.description));
            }
        }

        mView.setCreditScoreAdapter(adapter);
    }

    private void displayTimeAtAddress() {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_time_at_address));
        adapter.add(hint);

        TimeAtAddressVo[] typesList = mProjectConfig.timeAtAddressOpts.data;
        if (typesList != null) {
            for (TimeAtAddressVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.timeAtAddressId, type.description));
            }
        }

        mView.setTimeAtAddressAdapter(adapter);
    }

    public void errorReceived(String error) {
        runOnUiThread(() -> {
            mView.showLoading(false);
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    public void showRequiredFields() {
        RequiredDataPointVo[] requiredUserData = ConfigStorage.getInstance().getRequiredUserData();
        for(RequiredDataPointVo requiredDataPoint : requiredUserData) {
            switch(requiredDataPoint.type) {
                case PersonalName:
                    mView.showPersonalName();
                    break;
                case Phone:
                    mView.showPhoneNumber();
                    break;
                case Email:
                    mView.showEmail();
                    break;
                case BirthDate:
                    mView.showBirthday();
                    break;
                case SSN:
                    mView.showSSN();
                    break;
                case Address:
                    mView.showAddress();
                    break;
                case Housing:
                    displayHousingType();
                    mView.showHousing();
                    break;
                case IncomeSource:
                    displayEmploymentStatus();
                    displaySalaryFrequencies();
                    mView.showEmployment();
                    break;
                case Income:
                    displaySalaryFrequencies();
                    mView.showIncome();
                    break;
                case CreditScore:
                    displayCreditScore();
                    mView.showCreditScore();
                    break;
                case PayDayLoan:
                    mView.showPayDayLoan();
                    break;
                case MemberOfArmedForces:
                    mView.showMemberOfArmedForces();
                    break;
                case TimeAtAddress:
                    displayTimeAtAddress();
                    mView.showTimeAtAddress();
                    break;
            }
        }
    }

    private class LoadConfigTask extends AsyncTask<Void, Void, LoanPurposesResponseVo> {

        @Override
        protected LoanPurposesResponseVo doInBackground(Void... params) {
            return ConfigStorage.getInstance().getLoanPurposes();
        }

        @Override
        protected void onPostExecute(LoanPurposesResponseVo loanPurposes) {
            loanPurposesListRetrieved(loanPurposes);
            displayConfigElements();
            showRequiredFields();
            mView.showLoading(false);
        }
    }
}
