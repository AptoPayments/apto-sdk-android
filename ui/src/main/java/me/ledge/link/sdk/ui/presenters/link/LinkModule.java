package me.ledge.link.sdk.ui.presenters.link;

import android.app.Activity;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.responses.config.ConfigResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationSummaryResponseVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsSummaryListResponseVo;
import me.ledge.link.api.vos.responses.workflow.ActionVo;
import me.ledge.link.api.vos.responses.workflow.GenericMessageConfigurationVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageModule;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.storages.UIStorage;
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
        mShowGenericMessageModule.onFinish = this::showOrSkipLoanInfo;
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

    private void showLoanInfoOrBack() {
        if (isLoanInfoRequired()) {
            showLoanInfo();
        } else {
            if(mShowWelcomeScreen) {
                showWelcomeScreen();
            }
            else {
                showHomeActivity();
            }
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
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onUserHasAllRequiredData = null;
        userDataCollectorModule.onFinish = this::showOffersList;
        userDataCollectorModule.onBack = this::showLoanInfoOrBack;
        userDataCollectorModule.isUpdatingProfile = updateProfile;
        userDataCollectorModule.onNoTokenRetrieved = null;
        userDataCollectorModule.onTokenRetrieved = this::getOpenApplications;
        startModule(userDataCollectorModule);
    }

    private void collectUserData() {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onUserHasAllRequiredData = null;
        userDataCollectorModule.onFinish = this::showOffersList;
        userDataCollectorModule.onBack = this::showLoanInfoOrBack;
        userDataCollectorModule.isUpdatingProfile = false;
        userDataCollectorModule.onNoTokenRetrieved = null;
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
        currentActivity.startActivity(currentActivity.getIntent());
    }

    private void askDataCollectorIfUserHasAllRequiredData() {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onFinish = this::showOrSkipWelcomeScreen;
        userDataCollectorModule.onUserDoesNotHaveAllRequiredData = () -> {
            mUserHasAllRequiredData = false;
            showOrSkipWelcomeScreen();
        };
        userDataCollectorModule.onUserHasAllRequiredData = () -> {
            mUserHasAllRequiredData = true;
            showOrSkipWelcomeScreen();
        };
        userDataCollectorModule.onNoTokenRetrieved = null;
        userDataCollectorModule.onTokenRetrieved = this::getOpenApplications;
        startModule(userDataCollectorModule);
    }

    private void showOrSkipWelcomeScreen() {
        if(mShowWelcomeScreen) {
            showWelcomeScreen();
        }
        else {
            showOrSkipLoanInfo();
        }
    }

    private void projectConfigRetrieved(ConfigResponseVo configResponseVo) {
        mShowWelcomeScreen = (configResponseVo.welcomeScreenAction.status != 0);
        if(mShowWelcomeScreen) {
            mWelcomeScreenAction = configResponseVo.welcomeScreenAction;
        }
        getUserTokenFromDataCollector();
    }

    private void getUserTokenFromDataCollector() {
        UserDataCollectorModule userDataCollectorModule = UserDataCollectorModule.getInstance(this.getActivity());
        userDataCollectorModule.onTokenRetrieved = this::getOpenApplications;
        userDataCollectorModule.onNoTokenRetrieved = this::askDataCollectorIfUserHasAllRequiredData;
        userDataCollectorModule.onBack = this::showHomeActivity;
        userDataCollectorModule.isUpdatingProfile = false;
        startModule(userDataCollectorModule);
    }

    private void getOpenApplications() {
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.getPendingLoanApplicationsList(new ListRequestVo());
    }

    private void showError(String errorMessage) {
        if(!errorMessage.isEmpty()) {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        }
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
        else if(applicationsList.total_count == 1) {
            LoanApplicationSummaryResponseVo applicationSummary = applicationsList.data[0];
            LoanApplicationModule.getInstance(getActivity()).continueApplication(applicationSummary.id);
        }
        else {
            LoanApplicationModule loanApplicationModule = LoanApplicationModule.getInstance(getActivity());
            loanApplicationModule.onStartNewApplication = this::showLoanInfo;
            loanApplicationModule.startLoanApplicationSelector(applicationsList);
        }
    }
}
