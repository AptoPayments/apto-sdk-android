package com.shiftpayments.link.sdk.ui.presenters.link;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.ConfigResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.CallToActionVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.GenericMessageConfigurationVo;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.UserDataCollectorConfigurationVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import com.shiftpayments.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageModule;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import com.shiftpayments.link.sdk.ui.presenters.verification.AuthModule;
import com.shiftpayments.link.sdk.ui.presenters.verification.AuthModuleConfig;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

import java8.util.concurrent.CompletableFuture;

/**
 * Created by adrian on 29/12/2016.
 */

public class LinkModule extends ShiftBaseModule {

    private boolean mShowWelcomeScreen;
    private ActionVo mWelcomeScreenAction;

    public LinkModule(Activity activity, Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
    }

    @Override
    public void initialModuleSetup() {
        CompletableFuture
                .supplyAsync(()-> UIStorage.getInstance().getContextConfig())
                .exceptionally(ex -> {
                    showError(ex.getMessage());
                    return null;
                })
                .thenAccept(this::projectConfigRetrieved);
    }

    /**
     * Called when the get current applications response has been received.
     * @param applicationsList API response.
     */
    @Subscribe
    public void handleResponse(LoanApplicationsSummaryListResponseVo applicationsList) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        if(applicationsList.total_count == 0) {
            showLoanInfo();
        }
        else {
            LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(getActivity(), this::showLoanInfo, this::showHomeActivity);
            loanApplicationModule.startLoanApplicationSelector(applicationsList);
        }
    }

    @Subscribe
    public void handleUserData(DataPointList userData) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        UserStorage.getInstance().setUserData(userData);
        getOpenApplications();
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

    private void showWelcomeScreen() {
        GenericMessageConfigurationVo actionConfig = (GenericMessageConfigurationVo) mWelcomeScreenAction.configuration;
        ShowGenericMessageModule showGenericMessageModule = ShowGenericMessageModule.getInstance(this.getActivity(), this::checkTokenOrStartAuthModule, this::showHomeActivity, actionConfig);
        startModule(showGenericMessageModule);
    }

    private void showOrSkipLoanInfo() {
        if (isLoanInfoRequired()) {
            showLoanInfo();
        } else {
            startUserDataCollectorModule();
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
        Command onBack;
        if(mShowWelcomeScreen) {
            onBack = this::showWelcomeScreen;
        }
        else {
            onBack = this::showHomeActivity;
        }
        LoanInfoModule loanInfoModule = LoanInfoModule.getInstance(this.getActivity(), this::showOffersList, onBack);
        startModule(loanInfoModule);
    }

    private void startUserDataCollectorModule() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity(), this::showOffersList, this::showWelcomeScreenOrBack);
        userDataCollectorModule.setCallToActionConfig(getConfigForLink());
        startModule(userDataCollectorModule);
    }

    private UserDataCollectorConfigurationVo getConfigForLink() {
        return new UserDataCollectorConfigurationVo(getActivity().getString(R.string.id_verification_title_get_offers), new CallToActionVo(getActivity().getString(R.string.id_verification_next_button_get_offers)));
    }

    private void showOffersList() {
        LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(this.getActivity(), this.onFinish, this::showOrSkipLoanInfo);
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
            getUserInfo();
        }
        else {
            startAuthModule();
        }
    }

    private void startAuthModule() {
        DataPointList userData = UserStorage.getInstance().getUserData();
        ConfigResponseVo config = UIStorage.getInstance().getContextConfig();
        AuthModuleConfig authModuleConfig = new AuthModuleConfig(config.primaryAuthCredential, config.secondaryAuthCredential);
        AuthModule authModule = AuthModule.getInstance(this.getActivity(), userData, authModuleConfig, this::showOrSkipLoanInfo, this::showHomeActivity);
        authModule.onExistingUser = this::getOpenApplications;
        startModule(authModule);
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

    private void projectConfigRetrieved(ConfigResponseVo configResponseVo) {
        mShowWelcomeScreen = (configResponseVo.welcomeScreenAction.status != 0);
        if(mShowWelcomeScreen) {
            mWelcomeScreenAction = configResponseVo.welcomeScreenAction;
        }
        showOrSkipWelcomeScreen();
    }

    private void getUserInfo() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getCurrentUser(true);
    }

    private void getOpenApplications() {
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.getPendingLoanApplicationsList(new ListRequestVo());
    }
}
