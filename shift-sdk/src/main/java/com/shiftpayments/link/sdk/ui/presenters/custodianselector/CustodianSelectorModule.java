package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.SelectCustodianConfigurationVo;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.AddCustodianListActivity;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.CoinbaseActivity;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class CustodianSelectorModule extends ShiftBaseModule implements AddCustodianListDelegate,
        CoinbaseDelegate {

    private static CustodianSelectorModule instance;

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
        startActivity(CoinbaseActivity.class);
    }

    @Override
    public void addDwolla() {
        // TODO
    }

    @Override
    public void coinbaseTokensRetrieved(String accessToken, String refreshToken) {
        UserStorage.getInstance().setCoinbaseAccessToken(accessToken);
        UserStorage.getInstance().setCoinbaseRefreshToken(refreshToken);
        super.onFinish.execute();
    }

    @Override
    public void onCoinbaseException(ApiErrorVo error) {
        super.showError(error.toString());
    }
}