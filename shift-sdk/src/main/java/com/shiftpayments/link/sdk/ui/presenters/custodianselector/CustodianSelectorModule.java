package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectCustodianConfigurationVo;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.AddCustodianListActivity;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.OAuthActivity;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class CustodianSelectorModule extends ShiftBaseModule implements AddCustodianListDelegate,
        OAuthDelegate {

    private static CustodianSelectorModule instance;
    private String mProvider;
    private static CustodianSelectorDelegate mDelegate;

    public static synchronized CustodianSelectorModule getInstance(Activity activity, CustodianSelectorDelegate delegate, Command onFinish, Command onBack) {
        if (instance == null) {
            instance = new CustodianSelectorModule(activity, onFinish, onBack);
        }
        mDelegate = delegate;
        return instance;
    }

    private CustodianSelectorModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
    }

    @Override
    public void initialModuleSetup() {
        showCustodianSelectorScreen();
    }

    private void showCustodianSelectorScreen() {
        startActivity(AddCustodianListActivity.class);
    }

    public SelectCustodianConfigurationVo getConfiguration() {
        //TODO: hardcoding configuration until backend is ready
        return new SelectCustodianConfigurationVo(true, false);
    }

    @Override
    public void addCustodianListOnBackPressed() {
        super.onBack.execute();
    }

    @Override
    public void addCoinbase() {
        mProvider = "COINBASE";
        startOAuthActivity();
    }

    @Override
    public void addDwolla() {
        mProvider = "DWOLLA";
        startOAuthActivity();
    }

    @Override
    public String getProvider() {
        return mProvider;
    }

    @Override
    public void oAuthTokensRetrieved(String accessToken, String refreshToken) {
        mDelegate.onTokensRetrieved(accessToken, refreshToken);
        super.onFinish.execute();
    }

    @Override
    public void onOAuthError(ApiErrorVo error) {
        super.showError(error);
    }

    private void startOAuthActivity() {
        getActivity().startActivity(new Intent(getActivity(), OAuthActivity.class));
    }
}