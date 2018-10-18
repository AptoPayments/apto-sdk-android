package com.shiftpayments.link.sdk.cardexample;

import android.content.Context;
import android.os.AsyncTask;

import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.vos.ShiftSdkOptions;

import java.lang.ref.WeakReference;

public class InitSdkTask extends AsyncTask<Void, Void, ConfigResponseVo> {

    private final WeakReference<Context> mContext;
    private InitSdkTaskListener mListener;

    public InitSdkTask (Context context, InitSdkTaskListener listener){
        mContext = new WeakReference<>(context);
        mListener = listener;
    }

    @Override
    protected ConfigResponseVo doInBackground(Void... params) {
        ShiftSdkOptions options = new ShiftSdkOptions();
        options.features.put(ShiftSdkOptions.OptionKeys.showAddFundingSourceButton, true);
        options.features.put(ShiftSdkOptions.OptionKeys.showActivateCardButton, false);
        options.features.put(ShiftSdkOptions.OptionKeys.useEmbeddedMode, true);
        ShiftPlatform.initialize(mContext.get(), getApiKey(),
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

    private String getApiKey() {
        return KeysStorage.getApiKey(mContext.get(), getDefaultApiKey());
    }

    private String getDefaultApiKey() {
        return mContext.get().getResources().getString(R.string.shift_api_key);
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
