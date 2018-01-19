package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

import org.greenrobot.eventbus.Subscribe;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.SessionExpiredErrorVo;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import me.ledge.link.api.vos.responses.workflow.ActionVo;
import me.ledge.link.api.vos.responses.workflow.GenericMessageConfigurationVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.presenters.verification.AuthModule;
import me.ledge.link.sdk.ui.presenters.verification.AuthModuleConfig;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UIStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;

/**
 * Created by adrian on 29/12/2016.
 */

public class LinkModule extends LedgeBaseModule {

    public LinkModule(Activity activity) {
        super(activity);
    }
    private boolean mUserHasAllRequiredData;
    private boolean mShowWelcomeScreen;
    private ActionVo mWelcomeScreenAction;

    @Override
    public void initialModuleSetup() {
        mUserHasAllRequiredData = false;
        CompletableFuture
                .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                .exceptionally(ex -> {
                    showError(ex.getMessage());
                    return null;
                })
                .thenAccept(this::projectConfigRetrieved);
    }

    private void showWelcomeScreen() {
        GenericMessageConfigurationVo actionConfig = (GenericMessageConfigurationVo) mWelcomeScreenAction.configuration;
        ShowGenericMessageModule mShowGenericMessageModule = ShowGenericMessageModule.getInstance(this.getActivity(), actionConfig);
        mShowGenericMessageModule.onFinish = this::checkTokenOrStartAuthModule;
        mShowGenericMessageModule.onBack = this::showHomeActivity;
        startModule(mShowGenericMessageModule);
    }

    private void showOrSkipLoanInfo() {
        if (isLoanInfoRequired()) {
            showLoanInfo();
        } else {
            skipLoanInfo();
        }
    }

    private void showWelcomeScreenOrBack() {
        if(mShowWelcomeScreen) {
            showWelcomeScreen();
        }
        else {
            showHomeActivity();
        }
    }

    private boolean isLoanInfoRequired() {
        boolean shouldSkipLoanAmount = ConfigStorage.getInstance().getSkipLoanAmount();
        boolean shouldSkipLoanPurpose = ConfigStorage.getInstance().getSkipLoanPurpose();
        return !shouldSkipLoanAmount || !shouldSkipLoanPurpose;
    }

    private void showLoanInfo() {
        LoanInfoModule loanInfoModule = LoanInfoModule.getInstance(this.getActivity());
        loanInfoModule.userHasAllRequiredData = mUserHasAllRequiredData;
        if(mUserHasAllRequiredData) {
            loanInfoModule.onGetOffers = this::showOffersList;
            loanInfoModule.onFinish = this::showOffersList;
            loanInfoModule.onUpdateProfile = () -> startUserDataCollectorModule(true);
        }
        else {
            loanInfoModule.onGetOffers = null;
            loanInfoModule.onFinish = this::collectUserData;
        }
        if(mShowWelcomeScreen) {
            loanInfoModule.onBack = this::showWelcomeScreen;
        }
        else {
            loanInfoModule.onBack = this::showHomeActivity;
        }
        startModule(loanInfoModule);
    }

    private void skipLoanInfo() {
        if(mUserHasAllRequiredData) {
            showOffersList();
        }
        else {
            showUserDataCollector();
        }
    }

    private void showUserDataCollector() {
        startUserDataCollectorModule(false);
    }

    private void startUserDataCollectorModule(boolean updateProfile) {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onFinish = this::showOffersList;
        userDataCollectorModule.onBack = this::showWelcomeScreenOrBack;
        userDataCollectorModule.isUpdatingProfile = updateProfile;
        userDataCollectorModule.onTokenRetrieved = this::getOpenApplications;
        startModule(userDataCollectorModule);
    }

    private void collectUserData() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onFinish = this::showOffersList;
        userDataCollectorModule.onBack = this::showWelcomeScreenOrBack;
        userDataCollectorModule.isUpdatingProfile = false;
        userDataCollectorModule.onTokenRetrieved = null;
        startModule(userDataCollectorModule);
    }

    private void showOffersList() {
        mUserHasAllRequiredData = true;
        LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(this.getActivity());
        loanApplicationModule.onUpdateUserProfile = () -> startUserDataCollectorModule(true);
        loanApplicationModule.onBack = this::showOrSkipLoanInfo;
        startModule(loanApplicationModule);
    }

    private void showHomeActivity() {
        Activity currentActivity = this.getActivity();
        currentActivity.finish();
        Intent intent = currentActivity.getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
    }

    private void showOrSkipWelcomeScreen() {
        if(mShowWelcomeScreen) {
            showWelcomeScreen();
        }
        else {
            checkTokenOrStartAuthModule();
        }
    }

    private void checkTokenOrStartAuthModule() {
        if(isStoredUserTokenValid()) {
            getOpenApplications();
        }
        else {
            startAuthModule();
        }
    }

    private void startAuthModule() {
        DataPointList userData = UserStorage.getInstance().getUserData();
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        AuthModuleConfig authModuleConfig = new AuthModuleConfig(config.primaryAuthCredential, config.secondaryAuthCredential);
        AuthModule authModule = AuthModule.getInstance(this.getActivity(), userData, authModuleConfig);
        authModule.onExistingUser = this::getOpenApplications;
        authModule.onNewUserWithVerifiedPrimaryCredential = this::showOrSkipLoanInfo;
        authModule.onBack = this::showHomeActivity;
        authModule.onFinish = this::showHomeActivity;
        startModule(authModule);
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

    private void projectConfigRetrieved(ConfigResponseVo configResponseVo) {
        mShowWelcomeScreen = (configResponseVo.welcomeScreenAction.status != 0);
        if(mShowWelcomeScreen) {
            mWelcomeScreenAction = configResponseVo.welcomeScreenAction;
        }
        showOrSkipWelcomeScreen();
    }

    private void getOpenApplications() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.getPendingLoanApplicationsList(new ListRequestVo());
    }

    /**
     * Called when the get current applications response has been received.
     * @param applicationsList API response.
     */
    @Subscribe
    public void handleResponse(LoanApplicationsSummaryListResponseVo applicationsList) {
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        if(applicationsList.total_count == 0) {
            showLoanInfo();
        }
        else {
            LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(getActivity());
            loanApplicationModule.onStartNewApplication = this::showLoanInfo;
            loanApplicationModule.onBack = this::showHomeActivity;
            loanApplicationModule.onUpdateUserProfile = () -> startUserDataCollectorModule(true);
            loanApplicationModule.startLoanApplicationSelector(applicationsList);
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        showError(error.toString());
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
