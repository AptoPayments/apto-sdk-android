package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.SSN;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.OAuthCredentialVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetBalanceStoreRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.SetBalanceStoreResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.UserDataListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.UserResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.userdata.PersonalInformationConfirmationActivity;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PersonalInformationConfirmationDelegate;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.shiftpayments.link.sdk.ui.utils.DateUtil.BIRTHDATE_DATE_FORMAT;

/**
 * Created by adrian on 19/09/2018.
 */

public class BalanceStoreModule extends ShiftBaseModule implements CustodianSelectorDelegate,
        PersonalInformationConfirmationDelegate {

    private static BalanceStoreModule instance;
    private boolean updateUserRequired = false;
    private SetBalanceStoreRequestVo setBalanceStoreRequest;

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
        OAuthCredentialVo coinbaseCredentials = new OAuthCredentialVo(oAuthResponse.tokens.access, oAuthResponse.tokens.refresh);
        setBalanceStoreRequest = new SetBalanceStoreRequestVo("coinbase", coinbaseCredentials);
        if(oAuthResponse.userDataListVo != null) {
            startPersonalInformationConfirmationScreen(oAuthResponse.userDataListVo);
        }
        else {
            setBalanceStore();
        }
    }

    @Override
    public void personalInformationConfirmed() {
        updateUserRequired = true;
        setBalanceStore();
    }

    @Override
    public void personalInformationOnBackPressed() {
        startCustodianModule(onFinish, onBack);
    }

    @Subscribe
    public void handleSetBlanceStoreResponse(SetBalanceStoreResponseVo response) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        if(response.result.equals("valid")) {
            if(updateUserRequired) {
                ShiftSdk.getResponseHandler().subscribe(this);
                ShiftPlatform.updateUser(getUpdateUserRequest());
            }
            else {
                onFinish.execute();
            }
        }
        else {
            startCustodianModule(onFinish, onBack);
            ApiErrorUtil.showAlertDialog(response.errorCode);
        }
    }

    @Subscribe
    public void handleUpdateUserResponse(UserResponseVo response) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        onFinish.execute();
    }

    @Subscribe
    public void handleApiError(ApiErrorVo response) {
        startCustodianModule(onFinish, onBack);
        ApiErrorUtil.showErrorMessage(response, getActivity());
    }

    private void setBalanceStore() {
        ShiftSdk.getResponseHandler().subscribe(this);
        showLoading(true);
        ShiftPlatform.setBalanceStore(CardStorage.getInstance().getApplication().applicationId, setBalanceStoreRequest);
    }

    private void startCustodianModule(Command onFinish, Command onBack) {
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(
                this.getActivity(), this, onFinish, onBack);
        startModule(custodianSelectorModule);
    }

    private void startPersonalInformationConfirmationScreen(UserDataListResponseVo userDataList) {
        SoftReference<ShiftBaseModule> moduleSoftReference = new SoftReference<>(this);
        ModuleManager.getInstance().setModule(moduleSoftReference);
        DataPointList dataPointList = new DataPointList(DataPointList.ListType.userData);
        for(DataPointVo d : userDataList.data) {
            if(d != null) {
                dataPointList.add(d);
            }
        }
        UserStorage.getInstance().setUserData(dataPointList);
        getActivity().startActivity(new Intent(getActivity(), PersonalInformationConfirmationActivity.class));
    }

    private DataPointList getUpdateUserRequest() {
        DataPointList request = UserStorage.getInstance().getUserData();
        String primaryCredential = UIStorage.getInstance().getContextConfig().primaryAuthCredential;
        DataPointVo.DataPointType credentialType = DataPointVo.DataPointType.fromString(primaryCredential);
        request.removeDataPointsOf(credentialType);
        return request;
    }
}