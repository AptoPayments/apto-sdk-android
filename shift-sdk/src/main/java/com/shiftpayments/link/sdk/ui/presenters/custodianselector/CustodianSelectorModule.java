package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.AllowedBalanceType;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.AddCustodianListActivity;
import com.shiftpayments.link.sdk.ui.activities.custodianselector.OAuthActivity;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import java.util.ArrayList;


/**
 * Created by adrian on 29/12/2016.
 */

public class CustodianSelectorModule extends ShiftBaseModule implements AddCustodianListDelegate,
        OAuthDelegate {

    private static CustodianSelectorModule instance;
    private String mProvider;
    private static CustodianSelectorDelegate mDelegate;
    private ArrayList<AllowedBalanceType> balanceTypeList;

    public static synchronized CustodianSelectorModule getInstance(Activity activity, CustodianSelectorDelegate delegate, Command onFinish, Command onBack, ArrayList<AllowedBalanceType> balanceTypeList) {
        if (instance == null) {
            instance = new CustodianSelectorModule(activity, onFinish, onBack, balanceTypeList);
        }
        mDelegate = delegate;
        return instance;
    }

    private CustodianSelectorModule(Activity activity, Command onFinish, Command onBack, ArrayList<AllowedBalanceType> balanceTypeList) {
        super(activity, onFinish, onBack);
        this.balanceTypeList = balanceTypeList;
    }

    @Override
    public void initialModuleSetup() {
        showCustodianSelectorScreen();
    }

    private void showCustodianSelectorScreen() {
        //TODO: read balanceTypeList and show a selector in case there's more than one
        startActivity(AddCustodianListActivity.class);
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
    public AllowedBalanceType getAllowedBalanceType() {
        //TODO: send the balance type chosen by the user
        return balanceTypeList.get(0);
    }

    @Override
    public void onOauthPassed(OAuthStatusResponseVo oAuthResponse) {
        mDelegate.onTokensRetrieved(oAuthResponse);
    }

    @Override
    public void onOAuthError(ApiErrorVo error) {
        super.showError(error);
    }

    private void startOAuthActivity() {
        getActivity().startActivity(new Intent(getActivity(), OAuthActivity.class));
    }
}