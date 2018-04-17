package com.shift.link.sdk.ui.presenters.card;

import android.app.Activity;
import android.content.Intent;

import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;
import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shift.link.sdk.api.vos.responses.ApiErrorVo;
import com.shift.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shift.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shift.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shift.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;
import com.shift.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shift.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shift.link.sdk.sdk.ShiftLinkSdk;
import com.shift.link.sdk.sdk.storages.ConfigStorage;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.ShiftPlatform;
import com.shift.link.sdk.ui.activities.KycStatusActivity;
import com.shift.link.sdk.ui.activities.card.IssueVirtualCardActivity;
import com.shift.link.sdk.ui.activities.card.ManageCardActivity;
import com.shift.link.sdk.ui.presenters.custodianselector.CustodianSelectorModule;
import com.shift.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shift.link.sdk.ui.presenters.verification.AuthModule;
import com.shift.link.sdk.ui.presenters.verification.AuthModuleConfig;
import com.shift.link.sdk.ui.storages.CardStorage;
import com.shift.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shift.link.sdk.ui.storages.UIStorage;
import com.shift.link.sdk.ui.storages.UserStorage;
import com.shift.link.sdk.ui.workflow.Command;
import com.shift.link.sdk.ui.workflow.LedgeBaseModule;
import com.shift.link.sdk.ui.workflow.ModuleManager;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.shift.link.sdk.api.vos.datapoints.DataPointVo.DataPointType.PersonalName;

/**
 * Created by adrian on 23/02/2018.
 */

public class CardModule extends LedgeBaseModule {

    public CardModule(Activity activity) {
        super(activity);
    }
    private RequiredDataPointsListResponseVo mFinalRequiredUserData;
    private boolean mIsExistingUser;

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

    private boolean isStoredUserTokenValid() {
        boolean isPOSMode = ConfigStorage.getInstance().getPOSMode();
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity(), isPOSMode);
        boolean isTokenValid = !isPOSMode && userToken != null;
        if(isTokenValid) {
            ShiftPlatform.getApiWrapper().setBearerToken(userToken);
        }
        return isTokenValid;
    }

    public void showHomeActivity() {
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
        getActivity().startActivity(new Intent(getActivity(), IssueVirtualCardActivity.class));
    }

    public void startManageCardScreen() {
        setCurrentModule();
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

    public void startCustodianModule(Command onBackCallback, Command onFinishCallback) {
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(this.getActivity());
        custodianSelectorModule.onBack = onBackCallback;
        custodianSelectorModule.onFinish = ()->{
            setCurrentModule();
            onFinishCallback.execute();
        };
        startModule(custodianSelectorModule);
    }

    public void setCurrentModule() {
        WeakReference<LedgeBaseModule> moduleWeakReference = new WeakReference<>(this);
        ModuleManager.getInstance().setModule(moduleWeakReference);
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
        showHomeActivity();
    }
}

