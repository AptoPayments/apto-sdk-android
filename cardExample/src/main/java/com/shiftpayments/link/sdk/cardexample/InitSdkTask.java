package com.shiftpayments.link.sdk.cardexample;

import android.content.Context;
import android.os.AsyncTask;

import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.vos.ShiftSdkOptions;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class InitSdkTask extends AsyncTask<Void, Void, ConfigResponseVo> {

    private final WeakReference<Context> mContext;
    private InitSdkTaskListener mListener;

    public InitSdkTask (Context context, InitSdkTaskListener listener){
        mContext = new WeakReference<>(context);
        mListener = listener;
    }

    @Override
    protected ConfigResponseVo doInBackground(Void... params) {
        HashMap<ShiftSdkOptions.OptionKeys, Boolean> features = new HashMap<>();
        features.put(ShiftSdkOptions.OptionKeys.showActivateCardButton, false);
        features.put(ShiftSdkOptions.OptionKeys.showAddFundingSourceButton, true);
        ShiftSdkOptions options = new ShiftSdkOptions(features);
        ShiftPlatform.initialize(mContext.get(), getDeveloperKey(), getProjectToken(),
                getCertificatePinning(), getTrustSelfSignedCertificates(), getEnvironment(),
                null, options);
        return UIStorage.getInstance().getContextConfig();
    }

    @Override
    protected void onPostExecute(ConfigResponseVo result) {
        super.onPostExecute(result);
        mListener.onConfigRetrieved(result);
    }

    /**
     * @return The targeted environment (local, dev, stg, sbx or prd)
     */
    private String getEnvironment() {
        return KeysStorage.getEnvironment(mContext.get(), getDefaultEnvironment());
    }

    private String getDefaultEnvironment() {
        return mContext.get().getResources().getString(R.string.shift_environment);
    }

    /**
     * @return Link API dev key.
     */
    private String getDeveloperKey() {
        return KeysStorage.getDeveloperKey(mContext.get(), getDefaultDeveloperKey());
    }

    private String getDefaultDeveloperKey() {
        return mContext.get().getResources().getString(R.string.shift_developer_key);
    }

    /**
     * @return Link project token.
     */
    private String getProjectToken() {
        return KeysStorage.getProjectToken(mContext.get(), getDefaultProjectToken());
    }

    private String getDefaultProjectToken() {
        return mContext.get().getResources().getString(R.string.shift_project_token);
    }

    /**
     * @return If certificate pinning should be enabled
     */
    private boolean getCertificatePinning() {
        return mContext.get().getResources().getBoolean(R.bool.enable_certificate_pinning);
    }

    /**
     * @return If self signed certificates should be trusted
     */
    private boolean getTrustSelfSignedCertificates() {
        return mContext.get().getResources().getBoolean(R.bool.trust_self_signed_certificates);
    }
}
