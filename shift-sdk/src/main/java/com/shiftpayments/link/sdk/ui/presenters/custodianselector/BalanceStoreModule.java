package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.OAuthCredentialVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetBalanceStoreRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.SetBalanceStoreResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adrian on 19/09/2018.
 */

public class BalanceStoreModule extends ShiftBaseModule implements CustodianSelectorDelegate {

    private static BalanceStoreModule instance;

    public static synchronized BalanceStoreModule getInstance(Activity activity, Command onFinish, Command onBack) {
        if (instance == null) {
            instance = new BalanceStoreModule(activity, onFinish, onBack);
        }
        return instance;
    }

    private BalanceStoreModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
    }

    @Override
    public void initialModuleSetup() {
        startCustodianModule(onFinish, onBack);
    }


    @Override
    public void onTokensRetrieved(String accessToken, String refreshToken) {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        OAuthCredentialVo coinbaseCredentials = new OAuthCredentialVo(accessToken, refreshToken);
        SetBalanceStoreRequestVo setBalanceStoreRequest = new SetBalanceStoreRequestVo("coinbase", coinbaseCredentials);
        ShiftPlatform.setBalanceStore(CardStorage.getInstance().getApplication().applicationId, setBalanceStoreRequest);
    }

    @Subscribe
    public void handleResponse(SetBalanceStoreResponseVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        if(response.result.equals("valid")) {
            onFinish.execute();
        }
        else {
            startCustodianModule(onFinish, onBack);
            ApiErrorUtil.showAlertDialog(response.errorCode);
        }
    }

    private void startCustodianModule(Command onFinish, Command onBack) {
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(
                this.getActivity(), this, onFinish, onBack);
        startModule(custodianSelectorModule);
    }
}