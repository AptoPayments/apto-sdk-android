package me.ledge.link.sdk.ui.presenters.card;

import android.app.Activity;
import android.content.Intent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import me.ledge.link.sdk.api.vos.datapoints.Card;
import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.api.vos.responses.ApiErrorVo;
import me.ledge.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import me.ledge.link.sdk.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;
import me.ledge.link.sdk.api.vos.responses.workflow.CallToActionVo;
import me.ledge.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.card.IssueVirtualCardActivity;
import me.ledge.link.sdk.ui.activities.card.ManageCardActivity;
import me.ledge.link.sdk.ui.presenters.custodianselector.CustodianSelectorModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.presenters.verification.AuthModule;
import me.ledge.link.sdk.ui.presenters.verification.AuthModuleConfig;
import me.ledge.link.sdk.ui.storages.CardStorage;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;

import static me.ledge.link.sdk.api.vos.datapoints.DataPointVo.DataPointType.PersonalName;

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
            LedgeLinkUi.getApiWrapper().setBearerToken(userToken);
        }
        return isTokenValid;
    }

    private void showHomeActivity() {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        Activity currentActivity = this.getActivity();
        currentActivity.finish();
        Intent intent = currentActivity.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        if(mIsExistingUser) {
            userDataCollectorModule.onBack = this::issueVirtualCard;
        }
        else {
            userDataCollectorModule.onBack = this::showHomeActivity;
        }
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
        getActivity().startActivity(new Intent(getActivity(), IssueVirtualCardActivity.class));
    }

    private void startManageCardScreen() {
        getActivity().startActivity(new Intent(getActivity(), ManageCardActivity.class));
    }

    private void getUserInfo() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.getCurrentUser(true);
    }

    private void checkIfUserHasAnExistingCardOrIssueNewOne() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.getFinancialAccounts();
    }

    private void getCardData(String accountId) {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.getFinancialAccount(accountId);
    }

    private void startCustodianModule() {
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(this.getActivity());
        custodianSelectorModule.onBack = this::showHomeActivity;
        custodianSelectorModule.onFinish = this::collectFinalUserData;
        startModule(custodianSelectorModule);
    }

    /**
     * Called when the get financial accounts response or get current user response has been received.
     * @param dataPointList API response.
     */
    @Subscribe
    public void handleDataPointList(DataPointList dataPointList) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
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
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        CardStorage.getInstance().setCard(card);
        startManageCardScreen();
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

