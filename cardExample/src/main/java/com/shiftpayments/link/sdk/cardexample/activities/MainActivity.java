package com.shiftpayments.link.sdk.cardexample.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.cardexample.InitSdkTask;
import com.shiftpayments.link.sdk.cardexample.KeysStorage;
import com.shiftpayments.link.sdk.cardexample.InitSdkTaskListener;
import com.shiftpayments.link.sdk.cardexample.R;
import com.shiftpayments.link.sdk.cardexample.views.MainView;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;

import org.json.JSONException;

import io.branch.referral.Branch;

public class MainActivity extends AppCompatActivity implements MainView.ViewListener, InitSdkTaskListener {

    MainView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ShiftPlatform.hasUserToken(this)) {
            showLoadingSpinner();
            new InitSdkTask(this, this).execute();
        }
        else {
            mView = (MainView) View.inflate(this, R.layout.act_main, null);
            mView.setViewListener(this);
            setContentView(mView);
            mView.showLoading(true);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(ShiftPlatform.hasUserToken(this)) {
            return;
        }
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
            else if (error != null) {
                mView.showLoading(false);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
            new InitSdkTask(this, this).execute();
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

    protected String getDefaultDeveloperKey() {
        return getString(R.string.shift_developer_key);
    }

    protected String getDefaultProjectToken() {
        return getString(R.string.shift_project_token);
    }

    private String getBranchKey() {
        return getString(R.string.shift_branch_key);
    }

    private void showLoadingSpinner() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ProgressBar progressBar = new ProgressBar(MainActivity.this,null,android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150,150);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(progressBar,params);
        setContentView(layout);
    }

    @Override
    public void onConfigRetrieved(ConfigResponseVo config) {
        if(ShiftPlatform.hasUserToken(this)) {
            ShiftPlatform.startCardFlow(this);
        }
        else {
            configRetrieved(config);
        }
    }
}
