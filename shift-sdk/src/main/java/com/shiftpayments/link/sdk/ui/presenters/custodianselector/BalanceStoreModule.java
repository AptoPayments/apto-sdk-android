package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.OAuthCredentialVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetBalanceStoreRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.SetBalanceStoreResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.userdata.PersonalInformationConfirmationActivity;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PersonalInformationConfirmationDelegate;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;

/**
 * Created by adrian on 19/09/2018.
 */

public class BalanceStoreModule extends ShiftBaseModule implements CustodianSelectorDelegate,
        PersonalInformationConfirmationDelegate {

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
    public void onTokensRetrieved(OAuthStatusResponseVo oAuthResponse) {
        if(oAuthResponse.userDataListVo != null) {
            startPersonalInformationConfirmationScreen();
        }
        else {
            ShiftSdk.getResponseHandler().subscribe(this);
            OAuthCredentialVo coinbaseCredentials = new OAuthCredentialVo(oAuthResponse.tokens.access, oAuthResponse.tokens.refresh);
            SetBalanceStoreRequestVo setBalanceStoreRequest = new SetBalanceStoreRequestVo("coinbase", coinbaseCredentials);
            ShiftPlatform.setBalanceStore(CardStorage.getInstance().getApplication().applicationId, setBalanceStoreRequest);
        }
    }

    @Override
    public void personalInformationConfirmed() {
        // TODO: setBalanceStore and call PUT /user
        Log.d("ADRIAN", "personalInformationConfirmed: ");
    }

    @Override
    public void personalInformationOnBackPressed() {
        // TODO: show oAuth again
        Log.d("ADRIAN", "personalInformationOnBackPressed: ");
    }

    @Subscribe
    public void handleResponse(SetBalanceStoreResponseVo response) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
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

    private void startPersonalInformationConfirmationScreen() {
        SoftReference<ShiftBaseModule> moduleSoftReference = new SoftReference<>(this);
        ModuleManager.getInstance().setModule(moduleSoftReference);
        getActivity().startActivity(new Intent(getActivity(), PersonalInformationConfirmationActivity.class));
    }
}