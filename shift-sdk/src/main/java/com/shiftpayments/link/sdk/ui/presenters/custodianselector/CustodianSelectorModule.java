package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectCustodianConfigurationVo;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.AddCustodianListActivity;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.OAuthActivity;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class CustodianSelectorModule extends ShiftBaseModule implements AddCustodianListDelegate,
        OAuthDelegate {

    private static CustodianSelectorModule instance;
    private String mProvider;

    public static synchronized CustodianSelectorModule getInstance(Activity activity) {
        if (instance == null) {
            instance = new CustodianSelectorModule(activity);
        }
        return instance;
    }

    private CustodianSelectorModule(Activity activity) {
        super(activity);
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
        UserStorage.getInstance().setCoinbaseAccessToken(accessToken);
        UserStorage.getInstance().setCoinbaseRefreshToken(refreshToken);
        super.onFinish.execute();
    }

    @Override
    public void onOAuthError(ApiErrorVo error) {
        super.showError(error.toString());
    }

    private void startOAuthActivity() {
        getActivity().startActivity(new Intent(getActivity(), OAuthActivity.class));
    }
}