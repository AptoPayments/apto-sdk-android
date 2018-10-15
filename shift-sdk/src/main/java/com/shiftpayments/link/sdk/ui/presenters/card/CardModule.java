package com.shiftpayments.link.sdk.ui.presenters.card;

import android.app.Activity;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.BalanceDataVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardapplication.CardApplicationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.cardconfig.CardConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.KycStatusActivity;
import com.shiftpayments.link.sdk.ui.activities.card.ManageCardActivity;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.CustodianSelectorDelegate;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.CustodianSelectorModule;
import com.shiftpayments.link.sdk.ui.presenters.verification.AuthModule;
import com.shiftpayments.link.sdk.ui.presenters.verification.AuthModuleConfig;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.vos.ApplicationVo;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;
import com.shiftpayments.link.sdk.ui.workflow.WorkflowObject;

import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.concurrent.ExecutionException;

import java8.util.concurrent.CompletableFuture;
import java8.util.concurrent.CompletionException;

import static com.shiftpayments.link.sdk.sdk.ShiftSdk.getApiWrapper;

/**
 * Created by adrian on 23/02/2018.
 */

public class CardModule extends ShiftBaseModule implements ManageAccountDelegate, ManageCardDelegate,
        CardSettingsDelegate, KycStatusDelegate, CustodianSelectorDelegate {

    private NewCardModule mNewCardModule;

    public CardModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
        mNewCardModule = new NewCardModule(getActivity(), this::getApplicationStatus, this::startManageCardScreen,
                this::showHomeActivity, this::onIssueCardError);
    }

    @Override
    public void initialModuleSetup() {
        showLoading(true);
        setCurrentModule();
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getCardConfig())
                .exceptionally(ex -> {
                    showLoading(false);
                    ApiErrorUtil.showErrorMessage(ex, getActivity());
                    return null;
                })
                .thenAccept(this::handleConfig);
    }

    @Override
    public void addFundingSource(Command onFinishCallback) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        startCustodianModule(onFinishCallback, this::startManageCardScreen);
    }

    @Override
    public void onTokensRetrieved(OAuthStatusResponseVo oAuthResponse) {
        ShiftSdk.getResponseHandler().subscribe(this);
        String cardId = CardStorage.getInstance().getCard().mAccountId;
        BalanceDataVo balanceData = new BalanceDataVo("coinbase", oAuthResponse.tokens.access, oAuthResponse.tokens.refresh);
        ShiftPlatform.addUserBalance(cardId, balanceData);
        startManageCardScreen();
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

    @Override
    public void onManageCardBackPressed() {
        showHomeActivity();
    }

    /**
     * Called when the card config has been received.
     * @param config API response.
     */
    public void handleConfig(CardConfigResponseVo config) {
        if(isStoredUserTokenValid()) {
            getUserInfo();
        }
        else {
            UserStorage.getInstance().setUserData(null);
            startAuthModule();
        }
    }

    /**
     * Called when the get financial account response has been received.
     * @param card API response.
     */
    @Subscribe
    public void handleResponse(Card card) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        CardStorage.getInstance().setCard(card);

        if(card.kycStatus.equals(KycStatus.passed)) {
            startManageCardScreen();
        }
        else {
            startKycStatusScreen(card);
        }
    }

    /**
     * Called when the get financial accounts response or get current user response has been received.
     * @param dataPointList API response.
     */
    @Subscribe
    public void handleDataPointList(DataPointList dataPointList) {
        ShiftSdk.getResponseHandler().unsubscribe(this);
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
        super.showError(error);
    }

    /**
     * Called when session expired error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        super.handleSessionExpiredError(error);
        ShiftSdk.getResponseHandler().unsubscribe(this);
        showHomeActivity();
    }

    private boolean isStoredUserTokenValid() {
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity(), false);
        boolean isTokenValid = userToken != null;
        if(isTokenValid) {
            ShiftPlatform.getApiWrapper().setBearerToken(userToken);
        }
        return isTokenValid;
    }

    private void showHomeActivity() {
        ShiftSdk.getResponseHandler().unsubscribe(this);
        Activity currentActivity = this.getActivity();
        currentActivity.finish();
        Intent intent = currentActivity.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
    }

    private void startAuthModule() {
        ShiftSdk.getResponseHandler().subscribe(this);
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        AuthModuleConfig authModuleConfig = new AuthModuleConfig(config.primaryAuthCredential, config.secondaryAuthCredential);
        AuthModule authModule = AuthModule.getInstance(getActivity(), null, authModuleConfig, this::startNewCardModule, this::showHomeActivity);
        authModule.onExistingUser = this::checkIfUserHasAnExistingCardOrIssueNewOne;
        startModule(authModule);
    }

    private void startNewCardModule() {
        showLoading(false);
        ShiftSdk.getResponseHandler().unsubscribe(this);
        mNewCardModule.initialModuleSetup();
    }

    private void startCustodianModule(Command onFinish, Command onBack) {
        showLoading(false);
        CustodianSelectorModule custodianSelectorModule = CustodianSelectorModule.getInstance(
                this.getActivity(), this,
                ()->{
                    setCurrentModule();
                    onFinish.execute();
                    },
                onBack);
        startModule(custodianSelectorModule);
    }

    private ApplicationVo getApplicationStatus(WorkflowObject currentObject) {
        if(!(currentObject instanceof ApplicationVo)) {
            throw new RuntimeException("Current workflow object is not an application!");
        }
        ApplicationVo currentApplication = (ApplicationVo) currentObject;
        CardStorage.getInstance().setApplication(currentApplication);
        CompletableFuture<CardApplicationResponseVo> future = CompletableFuture.supplyAsync(() -> {
            try {
                return getApiWrapper().getCardApplicationStatus(currentApplication.applicationId);
            } catch (ApiException e) {
                throw new CompletionException(e);
            }
        });
        try {
            CardApplicationResponseVo applicationStatus = future.get();
            return new ApplicationVo(applicationStatus.id, applicationStatus.nextAction, applicationStatus.workflowObjectId);
        } catch (InterruptedException | ExecutionException e) {
            future.completeExceptionally(e);
            throw new CompletionException(e);
        }
    }

    private void startManageCardScreen() {
        setCurrentModule();
        ShiftSdk.getResponseHandler().unsubscribe(this);
        getActivity().startActivity(new Intent(getActivity(), ManageCardActivity.class));
    }

    private void getUserInfo() {
        ShiftSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getCurrentUser(true);
    }

    private void checkIfUserHasAnExistingCardOrIssueNewOne() {
        showLoading(true);
        ShiftSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getFinancialAccounts();
    }

    private void getCardData(String accountId) {
        ShiftSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getFinancialAccount(accountId);
    }

    private void setCurrentModule() {
        SoftReference<ShiftBaseModule> moduleSoftReference = new SoftReference<>(this);
        ModuleManager.getInstance().setModule(moduleSoftReference);
    }

    private void handleFinancialAccounts(DataPointList financialAccounts) {
        if(financialAccounts == null || financialAccounts.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount) == null) {
            startNewCardModule();
            return;
        }
        Card card = findFirstVirtualCard(financialAccounts.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount));
        if(card != null) {
            getCardData(card.mAccountId);
        }
        else {
            startNewCardModule();
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

    @Override
    public void onKycPassed() {
        startManageCardScreen();
    }

    private void startKycStatusScreen(Card card) {
        setCurrentModule();
        Intent intent = new Intent(getActivity(), KycStatusActivity.class);
        intent.putExtra("KYC_STATUS", card.kycStatus.toString());
        if(card.kycReason != null) {
            intent.putExtra("KYC_REASON", card.kycReason[0]);
        }
        getActivity().startActivity(intent);
    }

    private void onIssueCardError() {
        startKycStatusScreen(CardStorage.getInstance().getCard());
    }
}

