package com.shiftpayments.link.sdk.cardexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.cardexample.KeysStorage;
import com.shiftpayments.link.sdk.cardexample.R;
import com.shiftpayments.link.sdk.cardexample.views.MainView;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;

import org.json.JSONException;

import io.branch.referral.Branch;
import java8.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity implements MainView.ViewListener {

    MainView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mView = (MainView) View.inflate(this, R.layout.act_main, null);
        mView.setViewListener(this);
        setContentView(mView);
        mView.showLoading(true);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch.getInstance(this, getBranchKey());
        Branch branch = Branch.getAutoInstance(getApplicationContext());
        branch.initSession((referringParams, error) -> {
            if (error == null && referringParams.has(KeysStorage.PREFS_ENVIRONMENT)
                    && referringParams.has(KeysStorage.PREFS_PROJECT_KEY)
                    && referringParams.has(KeysStorage.PREFS_TEAM_KEY)) {
                boolean hasProjectChanged;
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
                    ShiftPlatform.clearUserToken(this);
                }
            }
            ShiftPlatform.initialize(this, getDeveloperKey(), getProjectToken(),
                    getCertificatePinning(), getTrustSelfSignedCertificates(), getEnvironment(), null);
            ShiftPlatform.setCoinbaseKeys(getCoinbaseClientId(), getCoinbaseClientSecret());
            CompletableFuture
                    .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                    .thenAccept(this::configRetrieved)
                    .exceptionally((e) -> {
                        this.runOnUiThread(() -> {
                            mView.showLoading(false);
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                        return null;
                    });
        }, this.getIntent().getData(), this);
    }

    @Override
    public void getStartedClickedHandler() {
        ShiftPlatform.startCardFlow(this);
    }

    private void configRetrieved(ConfigResponseVo configResponseVo) {
        runOnUiThread(()->{
            if(configResponseVo.logoURL != null && !configResponseVo.logoURL.isEmpty()) {
                mView.setLogo(configResponseVo.logoURL);
            }
            else {
                mView.setLogo();
            }

            if(configResponseVo.summary != null && !configResponseVo.summary.isEmpty()) {
                mView.setSummary(configResponseVo.summary);
            }

            mView.setColors();
            mView.showLoading(false);
        });
    }

    /**
     * @return The targeted environment (local, dev, stg, sbx or prd)
     */
    protected String getEnvironment() {
        return KeysStorage.getEnvironment(this, getDefaultEnvironment());
    }

    protected String getDefaultEnvironment() {
        return getString(R.string.shift_environment);
    }

    /**
     * @return Link API dev key.
     */
    protected String getDeveloperKey() {
        return KeysStorage.getDeveloperKey(this, getDefaultDeveloperKey());
    }

    protected String getDefaultDeveloperKey() {
        return getString(R.string.shift_developer_key);
    }

    /**
     * @return Link project token.
     */
    protected String getProjectToken() {
        return KeysStorage.getProjectToken(this, getDefaultProjectToken());
    }

    protected String getDefaultProjectToken() {
        return getString(R.string.shift_project_token);
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

    private String getBranchKey() {
        return getString(R.string.shift_branch_key);
    }

    private String getCoinbaseClientId() {
        return getString(R.string.shift_coinbase_client_id);
    }

    private String getCoinbaseClientSecret() {
        return getString(R.string.shift_coinbase_client_secret);
    }
}
