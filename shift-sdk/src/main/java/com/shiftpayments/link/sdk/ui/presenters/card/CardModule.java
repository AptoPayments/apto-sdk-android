package com.shiftpayments.link.sdk.ui.presenters.card;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.KycStatusActivity;
import com.shiftpayments.link.sdk.ui.activities.card.IssueVirtualCardActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.CustodianSelectorModule;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.presenters.verification.AuthModule;
import com.shiftpayments.link.sdk.ui.presenters.verification.AuthModuleConfig;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo.DataPointType.PersonalName;

/**
 * Created by adrian on 23/02/2018.
 */

public class CardModule extends ShiftBaseModule implements ManageAccountDelegate, ManageCardDelegate {

    private RequiredDataPointsListResponseVo mFinalRequiredUserData;
    private boolean mIsExistingUser;

    public CardModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        setCurrentModule();
        if(isStoredUserTokenValid()) {
            mIsExistingUser = true;
            getUserInfo();
        }
        else {
            mIsExistingUser = false;
            UserStorage.getInstance().setUserData(null);
            startAuthModule();
        }
    }

    @Override
    public void addFundingSource(Command onFinishCallback) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        startCustodianModule(this::startManageCardScreen, onFinishCallback);
    }

    @Override
    public void onSignOut() {
        ShiftPlatform.clearUserToken(getActivity());
        showHomeActivity();
    }

    @Override
    public void onSessionExpired(SessionExpiredErrorVo error) {
        this.handleSessionExpiredError(error);
    }

    /**
     * Called when the get financial account response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(Card card) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        CardStorage.getInstance().setCard(card);

        if(card.kycStatus.equals(KycStatus.passed)) {
            startManageCardScreen();
        }
        else {
            Intent intent = new Intent(getActivity(), KycStatusActivity.class);
            intent.putExtra("KYC_STATUS", card.kycStatus.toString());
            if(card.kycReason != null) {
                intent.putExtra("KYC_REASON", card.kycReason[0]);
            }
            getActivity().startActivity(intent);
        }
    }

    /**
     * Called when the get financial accounts response or get current user response has been received.
     * @param dataPointList API response.
     */
    @Subscribe
    public void handleDataPointList(DataPointList dataPointList) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        if(dataPointList.getType().equals(DataPointList.ListType.financialAccounts)) {
            handleFinancialAccounts(dataPointList);
        }
        else {
            handleUserData(dataPointList);
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        super.showError(error.toString());
    }

    /**
     * Called when session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        super.handleSessionExpiredError(error);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showHomeActivity();
    }

    private boolean isStoredUserTokenValid() {
        boolean isPOSMode = ConfigStorage.getInstance().getPOSMode();
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity(), isPOSMode);
        boolean isTokenValid = !isPOSMode && userToken != null;
        if(isTokenValid) {
            ShiftPlatform.getApiWrapper().setBearerToken(userToken);
        }
        return isTokenValid;
    }

    private void showHomeActivity() {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        Activity currentActivity = this.getActivity();
        currentActivity.finish();
        Intent intent = currentActivity.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
    }

    private void startAuthModule() {
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        AuthModuleConfig authModuleConfig = new AuthModuleConfig(config.primaryAuthCredential, config.secondaryAuthCredential);
        AuthModule authModule = AuthModule.getInstance(getActivity(), null, authModuleConfig);
        authModule.onExistingUser = () -> {
            mIsExistingUser = true;
            checkIfUserHasAnExistingCardOrIssueNewOne();
        };
        authModule.onNewUserWithVerifiedPrimaryCredential = this::collectInitialUserData;
        authModule.onBack = this::showHomeActivity;
        authModule.onFinish = this::collectInitialUserData;
        startModule(authModule);
    }

    private void collectInitialUserData() {
        storeFinalRequiredDataPoints();
        setInitialRequiredDataPoints();
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(getActivity());
        userDataCollectorModule.onFinish = this::startCustodianModule;
        userDataCollectorModule.onBack = this::showHomeActivity;
        userDataCollectorModule.isUpdatingProfile = false;
        userDataCollectorModule.onTokenRetrieved = null;
        startModule(userDataCollectorModule);
    }

    private void collectFinalUserData() {
        ConfigStorage.getInstance().setRequiredUserData(mFinalRequiredUserData);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(getActivity());
        UserDataCollectorConfigurationVo config = new UserDataCollectorConfigurationVo(getActivity().getString(R.string.id_verification_title_issue_card), new CallToActionVo(getActivity().getString(R.string.id_verification_next_button_issue_card)));
        userDataCollectorModule.setCallToActionConfig(config);
        userDataCollectorModule.onFinish = this::issueVirtualCard;
        userDataCollectorModule.onBack = this::showHomeActivity;
        userDataCollectorModule.isUpdatingProfile = mIsExistingUser;
        userDataCollectorModule.onTokenRetrieved = null;
        startModule(userDataCollectorModule);
    }

    private void setInitialRequiredDataPoints() {
        // TODO: we need to split the user data collection in two
        // 1) initial user data: first name + last name
        // 2) final user data: rest of the required datapoints
        RequiredDataPointsListResponseVo initialRequiredUserData = new RequiredDataPointsListResponseVo();
        RequiredDataPointVo[] requiredDataPointList = new RequiredDataPointVo[1];
        requiredDataPointList[0] = new RequiredDataPointVo(PersonalName);
        initialRequiredUserData.data = requiredDataPointList;
        ConfigStorage.getInstance().setRequiredUserData(initialRequiredUserData);
    }

    private void storeFinalRequiredDataPoints() {
        mFinalRequiredUserData = ConfigStorage.getInstance().getRequiredUserData();
        RequiredDataPointVo[] requiredDataPointArray = mFinalRequiredUserData.data;
        List<RequiredDataPointVo> requiredDataPointsList = new ArrayList<>(Arrays.asList(requiredDataPointArray));
        for (Iterator<RequiredDataPointVo> iter = requiredDataPointsList.listIterator(); iter.hasNext(); ) {
            RequiredDataPointVo dataPoint = iter.next();
            if (dataPoint.type.equals(PersonalName)) {
                iter.remove();
            }
        }

        mFinalRequiredUserData.data = requiredDataPointsList.toArray(new RequiredDataPointVo[requiredDataPointsList.size()]);
    }

    private void issueVirtualCard() {
        setCurrentModule();
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        getActivity().startActivity(new Intent(getActivity(), IssueVirtualCardActivity.class));
    }

    private void startManageCardScreen() {
        setCurrentModule();
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        getActivity().startActivity(new Intent(getActivity(), ManageCardActivity.class));
    }

    private void getUserInfo() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getCurrentUser(true);
    }

    private void checkIfUserHasAnExistingCardOrIssueNewOne() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getFinancialAccounts();
    }

    private void getCardData(String accountId) {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getFinancialAccount(accountId);
    }

    private void startCustodianModule() {
        startCustodianModule(this::showHomeActivity, this::collectFinalUserData);
    }

    private void startCustodianModule(Command onBackCallback, Command onFinishCallback) {
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(this.getActivity());
        custodianSelectorModule.onBack = onBackCallback;
        custodianSelectorModule.onFinish = ()->{
            setCurrentModule();
            onFinishCallback.execute();
        };
        startModule(custodianSelectorModule);
    }

    private void setCurrentModule() {
        SoftReference<ShiftBaseModule> moduleSoftReference = new SoftReference<>(this);
        ModuleManager.getInstance().setModule(moduleSoftReference);
    }

    private void handleFinancialAccounts(DataPointList financialAccounts) {
        if(financialAccounts == null || financialAccounts.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount) == null) {
            collectInitialUserData();
            return;
        }
        Card card = findFirstVirtualCard(financialAccounts.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount));
        if(card != null) {
            getCardData(card.mAccountId);
        }
        else {
            collectInitialUserData();
        }
    }

    private void handleUserData(DataPointList userData) {
        UserStorage.getInstance().setUserData(userData);
        checkIfUserHasAnExistingCardOrIssueNewOne();
    }

    private Card findFirstVirtualCard(List<DataPointVo> financialAccountsList) {
        for(DataPointVo dataPoint : financialAccountsList) {
            FinancialAccountVo financialAccount = (FinancialAccountVo) dataPoint;
            if(financialAccount.mAccountType.equals(FinancialAccountVo.FinancialAccountType.Card) &&
                    ((Card) financialAccount).cardIssuer.equalsIgnoreCase("SHIFT")) {
                return (Card) financialAccount;
            }
        }
        return null;
    }
}

