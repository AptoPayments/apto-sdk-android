package com.shift.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;

import com.shift.link.sdk.api.vos.responses.workflow.SelectCustodianConfigurationVo;
import com.shift.link.sdk.ui.activities.custodianselector.AddCustodianListActivity;
import com.shift.link.sdk.ui.activities.custodianselector.CoinbaseActivity;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.workflow.LedgeBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class CustodianSelectorModule extends LedgeBaseModule implements AddCustodianListDelegate,
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
    public void onCoinbaseException(Exception exception) {
        super.showError(exception.getMessage());
    }
}