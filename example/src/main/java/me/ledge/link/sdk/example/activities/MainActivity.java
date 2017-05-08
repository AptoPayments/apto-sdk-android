package me.ledge.link.sdk.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import io.branch.referral.Branch;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.example.KeysStorage;
import me.ledge.link.sdk.example.R;
import me.ledge.link.sdk.example.views.MainView;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.vos.LoanDataVo;

/**
 * Main display.
 * @author Wijnand
 */
public class MainActivity extends AppCompatActivity implements MainView.ViewListener {


    private MainView mView;
    static WeakHashMap<String, WeakReference<DataPointList>> SHARED_USER_DATA;
    static WeakHashMap<String, WeakReference<LoanDataVo>> SHARED_LOAN_DATA;
    static final String USER_DATA_KEY = "USER_DATA_KEY";
    static final String LOAN_DATA_KEY = "LOAN_DATA_KEY";

    /**
     * @return The targeted environment (local, dev, stg, sbx or prd)
     */
    protected String getEnvironment() {
        return KeysStorage.getEnvironment(this, getDefaultEnvironment());
    }

    protected String getDefaultEnvironment() {
        return getString(R.string.ledge_link_environment);
    }

    /**
     * @return Link API dev key.
     */
    protected String getDeveloperKey() {
        return KeysStorage.getDeveloperKey(this, getDefaultDeveloperKey());
    }

    protected String getDefaultDeveloperKey() {
        return getString(R.string.ledge_link_developer_key_dev);
    }

    /**
     * @return Link project token.
     */
    protected String getProjectToken() {
        return KeysStorage.getProjectToken(this, getDefaultProjectToken());
    }

    protected String getDefaultProjectToken() {
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
        SHARED_USER_DATA = new WeakHashMap<>();
        SHARED_LOAN_DATA = new WeakHashMap<>();

        mView = (MainView) View.inflate(this, R.layout.act_main, null);
        mView.setViewListener(this);
        setContentView(mView);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance(getApplicationContext());
        branch.initSession((referringParams, error) -> {
            if (error == null && referringParams.has(KeysStorage.PREFS_ENVIRONMENT)
                    && referringParams.has(KeysStorage.PREFS_PROJECT_KEY)
                    && referringParams.has(KeysStorage.PREFS_TEAM_KEY)) {
                boolean hasProjectChanged = false;
                try {
                    hasProjectChanged = KeysStorage.storeKeys(this, referringParams.getString(KeysStorage.PREFS_ENVIRONMENT),
                            referringParams.getString(KeysStorage.PREFS_PROJECT_KEY),
                            referringParams.getString(KeysStorage.PREFS_TEAM_KEY));
                } catch (JSONException e) {
                    hasProjectChanged = KeysStorage.storeKeys(this, getDefaultEnvironment(),
                            getDefaultProjectToken(),
                            getDefaultDeveloperKey());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if(hasProjectChanged) {
                    LedgeLinkUi.clearUserToken(this);
                }
            }
            LedgeLinkUi.setupLedgeLink(this, getDeveloperKey(), getProjectToken(),
                    getCertificatePinning(), getTrustSelfSignedCertificates(), getEnvironment());
        }, this.getIntent().getData(), this);
    }

    /** {@inheritDoc} */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /** {@inheritDoc} */
    @Override
    public void offersClickedHandler() {
        WeakReference<DataPointList> userData = new WeakReference<>(null);
        WeakReference<LoanDataVo> loanData = new WeakReference<>(null);;
        if(SHARED_USER_DATA.containsKey(USER_DATA_KEY)){
            userData = SHARED_USER_DATA.get(USER_DATA_KEY);
        }
        if(SHARED_LOAN_DATA.containsKey(LOAN_DATA_KEY)){
            loanData = SHARED_LOAN_DATA.get(LOAN_DATA_KEY);
        }
        LedgeLinkUi.startProcess(this, userData.get(), loanData.get());
    }

    @Override
    public void settingsClickedHandler() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
