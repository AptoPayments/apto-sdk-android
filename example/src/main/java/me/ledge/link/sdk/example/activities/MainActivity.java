package me.ledge.link.sdk.example.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.branch.referral.Branch;
import me.ledge.link.api.vos.datapoints.DataPointList;
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
    static HashMap<String, WeakReference<DataPointList>> SHARED_USER_DATA;
    static HashMap<String, WeakReference<LoanDataVo>> SHARED_LOAN_DATA;
    static final String USER_DATA_KEY = "USER_DATA_KEY";
    static final String LOAN_DATA_KEY = "LOAN_DATA_KEY";
    private static final String PREFS_FILE_NAME = "KeysFile";
    private static final String PREFS_ENVIRONMENT = "ENVIRONMENT";
    private static final String PREFS_PROJECT_KEY = "PROJECT_KEY";
    private static final String PREFS_TEAM_KEY = "TEAM_KEY";

    /**
     * @return The targeted environment (local, dev, stg, sbx or prd)
     */
    protected String getEnvironment() {
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_ENVIRONMENT, getDefaultEnvironment());
    }

    protected String getDefaultEnvironment() {
        return getString(R.string.ledge_link_environment);
    }

    /**
     * @return Link API dev key.
     */
    protected String getDeveloperKey() {
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_TEAM_KEY, getDefaultDeveloperKey());
    }

    protected String getDefaultDeveloperKey() {
        return getString(R.string.ledge_link_developer_key_dev);
    }

    /**
     * @return Link project token.
     */
    protected String getProjectToken() {
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        return settings.getString(PREFS_PROJECT_KEY, getDefaultProjectToken());
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
        SHARED_USER_DATA = new HashMap<>();
        SHARED_LOAN_DATA = new HashMap<>();

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
            if (error == null && referringParams.has(PREFS_ENVIRONMENT)
                    && referringParams.has(PREFS_PROJECT_KEY)
                    && referringParams.has(PREFS_TEAM_KEY)) {
                try {
                    storeKeys(referringParams.getString(PREFS_ENVIRONMENT),
                            referringParams.getString(PREFS_PROJECT_KEY),
                            referringParams.getString(PREFS_TEAM_KEY));
                } catch (JSONException e) {
                    storeKeys(getDefaultEnvironment(),
                            getDefaultProjectToken(),
                            getDefaultDeveloperKey());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, this.getIntent().getData(), this);

        LedgeLinkUi.setupLedgeLink(this, getDeveloperKey(), getProjectToken(),
                getCertificatePinning(), getTrustSelfSignedCertificates(), getEnvironment());
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

    private void storeKeys(String environment, String projectKey, String teamKey) {
        SharedPreferences settings = this.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREFS_ENVIRONMENT, environment);
        editor.putString(PREFS_PROJECT_KEY, projectKey);
        editor.putString(PREFS_TEAM_KEY, teamKey);
        editor.apply();
    }
}
