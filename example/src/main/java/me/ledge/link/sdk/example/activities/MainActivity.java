package me.ledge.link.sdk.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import me.ledge.common.utils.android.AndroidUtils;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.api.wrappers.retrofit.two.RetrofitTwoLinkApiWrapper;
import me.ledge.link.sdk.example.R;
import me.ledge.link.sdk.example.views.MainView;
import me.ledge.link.sdk.imageloaders.volley.VolleyImageLoader;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.eventbus.utils.EventBusHandlerConfigurator;
import me.ledge.link.sdk.ui.utils.HandlerConfigurator;
import me.ledge.link.sdk.ui.vos.LoanDataVo;

/**
 * Main display.
 * @author Wijnand
 */
public class MainActivity extends AppCompatActivity implements MainView.ViewListener {

    private MainView mView;
    static HashMap<String, WeakReference<DataPointList>> SHARED_USER_DATA;
    static HashMap<String, WeakReference<LoanDataVo>> SHARED_LOAN_DATA;
    static final String USER_DATA_KEY = "USER_DATA_KEY";
    static final String LOAN_DATA_KEY = "LOAN_DATA_KEY";

    /**
     * Sets up the Ledge Link SDK.
     */
    private void setupLedgeLink() {
        AndroidUtils utils = new AndroidUtils();
        HandlerConfigurator configurator = new EventBusHandlerConfigurator();

        LinkApiWrapper apiWrapper = new RetrofitTwoLinkApiWrapper();
        apiWrapper.setApiEndPoint(getApiEndPoint(), getCertificatePinning(), getTrustSelfSignedCertificates());
        apiWrapper.setBaseRequestData(getDeveloperKey(), utils.getDeviceSummary(), getCertificatePinning(), getTrustSelfSignedCertificates());
        apiWrapper.setProjectToken(getProjectToken());

        LedgeLinkUi.setApiWrapper(apiWrapper);
        LedgeLinkUi.setImageLoader(new VolleyImageLoader(this));
        LedgeLinkUi.setHandlerConfiguration(configurator);
        LedgeLinkUi.trustSelfSigned = getTrustSelfSignedCertificates();
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

    /**
     * @return If certificate pinning should be enabled
     */
    protected boolean getCertificatePinning() {
        return this.getResources().getBoolean(R.bool.enable_certificate_pinning);
    }

    /**
     * @return If self signed certificates should be trusted
     */
    protected boolean getTrustSelfSignedCertificates() {
        return this.getResources().getBoolean(R.bool.trust_self_signed_certificates);
    }

    /** {@inheritDoc} */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHARED_USER_DATA = new HashMap<>();
        SHARED_LOAN_DATA = new HashMap<>();
        setupLedgeLink();

        mView = (MainView) View.inflate(this, R.layout.act_main, null);
        mView.setViewListener(this);
        setContentView(mView);
    }

    /** {@inheritDoc} */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /** {@inheritDoc} */
    @Override
    public void offersClickedHandler() {
        if(SHARED_USER_DATA.containsKey(USER_DATA_KEY) && SHARED_LOAN_DATA.containsKey(LOAN_DATA_KEY)) {
            WeakReference<DataPointList> userData = SHARED_USER_DATA.get(USER_DATA_KEY);
            WeakReference<LoanDataVo> loanData = SHARED_LOAN_DATA.get(LOAN_DATA_KEY);
            LedgeLinkUi.startProcess(this, userData.get(), loanData.get());
        }
        else {
            LedgeLinkUi.startProcess(this, null, null);
        }
    }

    @Override
    public void settingsClickedHandler() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
