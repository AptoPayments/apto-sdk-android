package me.ledge.link.sdk.example.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.common.utils.android.AndroidUtils;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.responses.config.LoanPurposeVo;
import me.ledge.link.api.vos.responses.config.LoanPurposesResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.api.wrappers.retrofit.two.RetrofitTwoLinkApiWrapper;
import me.ledge.link.sdk.example.R;
import me.ledge.link.sdk.example.views.MainView;
import me.ledge.link.sdk.handlers.eventbus.utils.EventBusHandlerConfigurator;
import me.ledge.link.sdk.imageloaders.volley.VolleyImageLoader;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.storages.LinkStorage;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;
import me.ledge.link.sdk.ui.vos.IdDescriptionPairDisplayVo;
import me.ledge.link.sdk.ui.vos.LoanDataVo;
import me.ledge.link.sdk.ui.widgets.HintArrayAdapter;

/**
 * Main display.
 * @author Wijnand
 */
public class MainActivity extends AppCompatActivity implements MainView.ViewListener {

    private MainView mView;

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
    private DataPointList createStartData() {
        DataPointList data = new DataPointList();
        boolean dataSet = false;

        LoanDataVo loanData = new LoanDataVo();
        if (hasValue(mView.getLoanAmount())) {
            loanData.loanAmount = parseIntSafely(mView.getLoanAmount());
            dataSet = true;
        }
        if (mView.getLoanPurpose() != null) {
            loanData.loanPurpose = mView.getLoanPurpose();
            dataSet = true;
        }
        LinkStorage.getInstance().setLoanData(loanData);
        if (hasValue(mView.getFirstName()) && hasValue(mView.getLastName())) {
            data.add(new DataPointVo.PersonalName(mView.getFirstName(), mView.getLastName(), false));
            dataSet = true;
        }
        if (hasValue(mView.getEmail())) {
            data.add(new DataPointVo.Email(mView.getEmail(), false));
            dataSet = true;
        }
        if (hasValue(mView.getPhoneNumber())) {
            data.add(new DataPointVo.PhoneNumber(mView.getPhoneNumber(), false));
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
        DataPointVo.Address addressDataPoint = new DataPointVo.Address(address, apartment, "US", city, state, zip, false);
        data.add(addressDataPoint);
        if (hasValue(mView.getIncome())) {
            data.add(new DataPointVo.Income(-1, parseIntSafely(mView.getIncome()), false));
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

    /**
     * Sets up the Ledge Link SDK.
     */
    private void setupLedgeLink() {
        AndroidUtils utils = new AndroidUtils();
        HandlerConfigurator configurator = new EventBusHandlerConfigurator();

        LinkApiWrapper apiWrapper = new RetrofitTwoLinkApiWrapper();
        apiWrapper.setApiEndPoint(getApiEndPoint());
        apiWrapper.setBaseRequestData(getDeveloperKey(), utils.getDeviceSummary());
        apiWrapper.setProjectToken(getProjectToken());

        LedgeLinkUi.setApiWrapper(apiWrapper);
        LedgeLinkUi.setImageLoader(new VolleyImageLoader(this));
        LedgeLinkUi.setHandlerConfiguration(configurator);
    }

    /**
     * @return Link API end point.
     */
    protected String getApiEndPoint() {
        return getString(R.string.ledge_link_url_dev);
    }

    /**
     * @return Link API dev key.
     */
    protected String getDeveloperKey() {
        return getString(R.string.ledge_link_developer_key_dev);
    }

    /**
     * @return Link project token.
     */
    protected String getProjectToken() {
        return getString(R.string.ledge_link_project_token);
    }

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLedgeLink();

        mView = (MainView) View.inflate(this, R.layout.act_main, null);
        mView.showLoading(true);
        mView.setViewListener(this);

        setContentView(mView);

        // Load the loan purpose list so we can create a drop-down.
        LedgeLinkUi.getHandlerConfiguration().getResponseHandler().subscribe(this);
        LedgeLinkUi.getLoanPurposesList();
    }

    /** {@inheritDoc} */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LedgeLinkUi.getHandlerConfiguration().getResponseHandler().unsubscribe(this);
    }

    /** {@inheritDoc} */
    @Override
    public void offersClickedHandler() {
        LedgeLinkUi.startProcess(this, createStartData());
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
        mView.setAddress(getString(R.string.data_michael_address));
        mView.setCity(getString(R.string.data_michael_city));
        mView.setState(getString(R.string.data_michael_state));
        mView.setZipCode(getString(R.string.data_michael_zip));
        mView.setIncome(getString(R.string.data_michael_income));
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
        mView.setAddress("");
        mView.setApartmentNumber("");
        mView.setCity("");
        mView.setState("");
        mView.setZipCode("");
        mView.setIncome("");
    }

    /**
     * Called when the loan purpose list has been received from the API.
     * @param response API response.
     */
    @Subscribe
    public void purposeListLoadedHandler(LoanPurposesResponseVo response) {
        HintArrayAdapter<IdDescriptionPairDisplayVo> adapter
                = new HintArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);

        IdDescriptionPairDisplayVo hint =
                new IdDescriptionPairDisplayVo(-1, getString(me.ledge.link.sdk.ui.R.string.loan_amount_purpose_hint));
        adapter.add(hint);

        if (response.data != null) {
            for (LoanPurposeVo purpose : response.data) {
                adapter.add(new IdDescriptionPairDisplayVo(purpose.loan_purpose_id, purpose.description));
            }
        }

        mView.setPurposeAdapter(adapter);
        mView.showLoading(false);
    }

    /**
     * Called when an API error occurred.
     * @param response The error.
     */
    @Subscribe
    public void apiErrorHandler(ApiErrorVo response) {
        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
        mView.showLoading(false);
    }
}
