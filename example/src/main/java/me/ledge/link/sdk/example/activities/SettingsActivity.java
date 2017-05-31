package me.ledge.link.sdk.example.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.api.vos.datapoints.Address;
import me.ledge.link.api.vos.datapoints.ArmedForces;
import me.ledge.link.api.vos.datapoints.Birthdate;
import me.ledge.link.api.vos.datapoints.CreditScore;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.Email;
import me.ledge.link.api.vos.datapoints.Employment;
import me.ledge.link.api.vos.datapoints.Housing;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.api.vos.datapoints.PaydayLoan;
import me.ledge.link.api.vos.datapoints.PersonalName;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.api.vos.datapoints.SSN;
import me.ledge.link.api.vos.datapoints.TimeAtAddress;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.config.EmploymentStatusVo;
import me.ledge.link.api.vos.responses.config.HousingTypeVo;
import me.ledge.link.api.vos.responses.config.LoanPurposeVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointsListResponseVo;
import me.ledge.link.api.vos.responses.config.SalaryFrequencyVo;
import me.ledge.link.api.vos.responses.config.TimeAtAddressVo;
import me.ledge.link.sdk.example.R;
import me.ledge.link.sdk.example.views.SettingsView;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.vos.LoanDataVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;

/**
 * Settings display.
 * @author Adrian
 */
public class SettingsActivity extends AppCompatActivity implements SettingsView.ViewListener {

    private SettingsView mView;
    private ConfigResponseVo mProjectConfig;

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
     * @return Pre-fill data for the Ledge Line SDK to use.
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
     * @return Pre-fill data for the Ledge Line SDK to use.
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
        if ((mView.getEmploymentStatus() != null && mView.getEmploymentStatus().getKey() != -1) &&
                (mView.getSalaryFrequency() != null && mView.getSalaryFrequency().getKey() != -1)) {
            data.add(new Employment(mView.getEmploymentStatus().getKey(), mView.getSalaryFrequency().getKey(), false, false));
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

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (SettingsView) View.inflate(this, R.layout.act_settings, null);
        mView.showLoading(true);
        mView.setViewListener(this);

        setContentView(mView);

        if( SharedPreferencesStorage.getUserToken(this, false) != null) {
            mView.setUserToken(UserStorage.getInstance().getBearerToken());
        }

        setUpToolbar();
        UIStorage.getInstance().init();

        // Load the loan purpose list so we can create a drop-down.
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getLoanPurposes())
                .exceptionally(ex -> {
                    errorReceived(ex.getMessage());
                    return null;
                })
                .thenAccept(this::loanPurposesListRetrieved);

        displayConfigElements();
        showRequiredFields();
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
    protected void onDestroy() {
        super.onDestroy();
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
        mView.setEmploymentStatus(Integer.parseInt(getString(R.string.data_michael_employment_status)));
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
        mView.setEmploymentStatus(0);
        mView.setCreditScore(0);
        mView.setTimeAtAddress(0);
    }

    @Override
    public void clearUserTokenClickedHandler() {
        mView.setUserToken("");
        LedgeLinkUi.clearUserToken(this);
        MainActivity.SHARED_USER_DATA.put(MainActivity.USER_DATA_KEY, new WeakReference<>(null));
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
            mView.showLoading(false);
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
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_employment_status));
        adapter.add(hint);

        EmploymentStatusVo[] typesList = mProjectConfig.employmentStatusOpts.data;
        if (typesList != null) {
            for (EmploymentStatusVo type : typesList) {
                adapter.add(new IdDescriptionPairDisplayVo(type.employment_status_id, type.description));
            }
        }

        mView.setEmploymentStatusAdapter(adapter);
    }

    private void displayCreditScore() {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(R.string.main_input_credit_score));
        adapter.add(hint);
        adapter.add(new IdDescriptionPairDisplayVo(1, getString(me.ledge.link.sdk.ui.R.string.credit_score_excellent)));
        adapter.add(new IdDescriptionPairDisplayVo(2, getString(me.ledge.link.sdk.ui.R.string.credit_score_good)));
        adapter.add(new IdDescriptionPairDisplayVo(3, getString(me.ledge.link.sdk.ui.R.string.credit_score_fair)));
        adapter.add(new IdDescriptionPairDisplayVo(4, getString(me.ledge.link.sdk.ui.R.string.credit_score_poor)));

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
        RequiredDataPointsListResponseVo requiredUserData = ConfigStorage.getInstance().getRequiredUserData();
        for(RequiredDataPointVo requiredDataPoint : requiredUserData.data) {
            switch(requiredDataPoint.type) {
                case PersonalName:
                    mView.showPersonalName();
                    break;
                case PhoneNumber:
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
                case Employment:
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
}
