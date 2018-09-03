package com.shiftpayments.link.sdk.ui.presenters.verification;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.LoginRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SessionExpiredErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.CreateUserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.LoginUserResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.BaseVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.sdk.storages.ConfigStorage;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.userdata.EmailActivity;
import com.shiftpayments.link.sdk.ui.activities.userdata.PhoneActivity;
import com.shiftpayments.link.sdk.ui.activities.verification.BirthdateVerificationActivity;
import com.shiftpayments.link.sdk.ui.activities.verification.EmailVerificationActivity;
import com.shiftpayments.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import com.shiftpayments.link.sdk.ui.presenters.userdata.EmailDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PhoneDelegate;
import com.shiftpayments.link.sdk.ui.storages.SharedPreferencesStorage;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.workflow.Command;
import com.shiftpayments.link.sdk.ui.workflow.ShiftBaseModule;

import org.greenrobot.eventbus.Subscribe;

public class AuthModule extends ShiftBaseModule implements PhoneDelegate, EmailDelegate,
        PhoneVerificationDelegate, EmailVerificationDelegate, BirthdateVerificationDelegate {

    private static AuthModule instance;
    public Command onExistingUser;

    private DataPointList mInitialUserData;
    private AuthModuleConfig mConfig;

    private AuthModule(Activity activity, DataPointList initialUserData, AuthModuleConfig config,
                       Command onFinish, Command onBack) {
        super(activity, onFinish, onBack);
        if (initialUserData == null) {
            mInitialUserData = new DataPointList();
        } else {
            mInitialUserData = initialUserData;
        }
        mConfig = config;
        SharedPreferencesStorage.storeCredentials(getActivity(), mConfig.primaryCredentialType.getType(), mConfig.secondaryCredentialType.getType());
    }

    public static synchronized AuthModule getInstance(Activity activity, DataPointList initialUserData, AuthModuleConfig config, Command onFinish, Command onBack) {
        if (instance == null) {
            instance = new AuthModule(activity, initialUserData, config, onFinish, onBack);
        }
        else {
            instance.onFinish = onFinish;
            instance.onBack = onBack;
        }
        return instance;
    }

    @Override
    public void initialModuleSetup() {
        startPrimaryCredentialActivity();
    }

    /**
     * Called when the get current user info response has been received.
     *
     * @param userInfo API response.
     */
    @Subscribe
    public void handleResponse(DataPointList userInfo) {
        showLoading(false);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        UserStorage.getInstance().setUserData(userInfo);
        onExistingUser.execute();
    }

    /**
     * Called when the login response has been received.
     *
     * @param response API response.
     */
    @Subscribe
    public void handleToken(LoginUserResponseVo response) {
        if (response != null) {
            storeToken(response.user_token);
            getCurrentUserOrContinue(ConfigStorage.getInstance().getPOSMode());
        }
    }

    /**
     * Called when the create user response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleToken(CreateUserResponseVo response) {
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        showLoading(false);
        if (response != null) {
            storeToken(response.user_token);
        }
        onFinish.execute();
    }

    /**
     * Called when the empty response of the Register Push Notifications Task has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleApiEmptyResponse(ApiEmptyResponseVo response) {
    }


    /**
     * Called when an API error has been received.
     *
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        showLoading(false);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        ApiErrorUtil.showErrorMessage(error, getActivity());
        stopModule();
    }

    /**
     * Called when an API error has been received.
     *
     * @param error API error.
     */
    @Subscribe
    public void handleSessionExpiredError(SessionExpiredErrorVo error) {
        showLoading(false);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        ShiftPlatform.clearUserToken(getActivity());
        ApiErrorUtil.showErrorMessage(error, getActivity());
        stopModule();
    }

    @Override
    public void phoneStored() {
        startActivity(PhoneVerificationActivity.class);
    }

    @Override
    public void phoneOnBackPressed() {
        if (mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Phone)) {
            super.onBack.execute();
        } else {
            startPrimaryCredentialActivity();
        }
    }

    @Override
    public void emailStored() {
        startActivity(EmailVerificationActivity.class);
    }

    @Override
    public void emailOnBackPressed() {
        if (mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Email)) {
            super.onBack.execute();
        } else {
            startPrimaryCredentialActivity();
        }
    }

    @Override
    public void phoneVerificationSucceeded(VerificationResponseVo verification) {
        BaseVerificationResponseVo secondaryCredential = verification.secondary_credential;
        if (secondaryCredential == null) {
            createUser();
            return;
        }
        DataPointList userData = UserStorage.getInstance().getUserData();
        switch (DataPointVo.DataPointType.fromString(secondaryCredential.verification_type)) {
            case Email:
                DataPointVo baseEmail = mInitialUserData.getUniqueDataPoint(DataPointVo.DataPointType.Email, new Email());
                baseEmail.setVerification(new VerificationVo(secondaryCredential.verification_id, secondaryCredential.verification_type));
                userData.add(baseEmail);
                UserStorage.getInstance().setUserData(userData);
                startActivity(EmailVerificationActivity.class);
                break;
            case BirthDate:
                DataPointVo baseBirthdate = mInitialUserData.getUniqueDataPoint(DataPointVo.DataPointType.BirthDate, new Birthdate());
                baseBirthdate.setVerification(new VerificationVo(secondaryCredential.verification_id, secondaryCredential.verification_type));
                userData.add(baseBirthdate);
                UserStorage.getInstance().setUserData(userData);
                startActivity(BirthdateVerificationActivity.class);
                break;
        }
    }

    @Override
    public void phoneVerificationOnBackPressed() {
        startActivity(PhoneActivity.class);
    }

    @Override
    public void emailVerificationSucceeded(VerificationResponseVo verification) {

        if (mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Email)) {
            BaseVerificationResponseVo secondaryCredential = verification.secondary_credential;
            if (secondaryCredential == null) {
                createUser();
                return;
            }
            DataPointList userData = UserStorage.getInstance().getUserData();
            switch (DataPointVo.DataPointType.fromString(secondaryCredential.verification_type)) {
                case Phone:
                    DataPointVo basePhone = mInitialUserData.getUniqueDataPoint(DataPointVo.DataPointType.Phone, new Email());
                    basePhone.setVerification(new VerificationVo(secondaryCredential.verification_id, secondaryCredential.verification_type));
                    userData.add(basePhone);
                    UserStorage.getInstance().setUserData(userData);
                    startActivity(PhoneActivity.class);
                    break;
                case BirthDate:
                    DataPointVo baseBirthdate = mInitialUserData.getUniqueDataPoint(DataPointVo.DataPointType.BirthDate, new Birthdate());
                    baseBirthdate.setVerification(new VerificationVo(secondaryCredential.verification_id, secondaryCredential.verification_type));
                    userData.add(baseBirthdate);
                    UserStorage.getInstance().setUserData(userData);
                    startActivity(BirthdateVerificationActivity.class);
                    break;
            }
        } else {
            loginUser();
        }
    }

    @Override
    public void emailVerificationOnBackPressed() {
        startActivity(EmailActivity.class);
    }

    @Override
    public void birthdateSucceeded() {
        loginUser();
    }

    @Override
    public void birthdateOnBackPressed() {
        startPrimaryCredentialActivity();
    }

    @Override
    public boolean isStartVerificationRequired() {
        return mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Email);
    }


    private void startPrimaryCredentialActivity() {
        if (mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Phone)) {
            startActivity(PhoneActivity.class);
        } else if (mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Email)) {
            startActivity(EmailActivity.class);
        } else {
            ApiErrorUtil.showErrorMessage("Configured primary credential is not supported!", getActivity());
        }
    }

    private void loginUser() {
        showLoading(true);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.loginUser(getLoginData());
    }

    private void createUser() {
        showLoading(true);
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.createUser(UserStorage.getInstance().getUserData());
    }

    private void storeToken(String token) {
        UserStorage.getInstance().setBearerToken(token);
        SharedPreferencesStorage.storeUserToken(getActivity(), token);
        SharedPreferencesStorage.storeCredentials(getActivity(), mConfig.primaryCredentialType.getType(), mConfig.secondaryCredentialType.getType());
    }

    private void getCurrentUserOrContinue(boolean isPOSMode) {
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity(), isPOSMode);
        if (!isPOSMode && userToken != null) {
            ShiftPlatform.getApiWrapper().setBearerToken(userToken);
            ShiftPlatform.getCurrentUser(true);
        }
    }

    private LoginRequestVo getLoginData() {
        DataPointList base = UserStorage.getInstance().getUserData();
        DataPointVo primaryDataPoint = base.getUniqueDataPoint(mConfig.primaryCredentialType, null);
        DataPointVo secondaryDataPoint = base.getUniqueDataPoint(mConfig.secondaryCredentialType, null);

        VerificationVo[] verificationList = new VerificationVo[2];
        verificationList[0] = primaryDataPoint.getVerification();
        verificationList[1] = secondaryDataPoint.getVerification();

        ListRequestVo<VerificationVo[]> verifications = new ListRequestVo<>();
        verifications.data = verificationList;

        return new LoginRequestVo(verifications);
    }

    private void stopModule() {
        if (super.onFinish != null) {
            super.onFinish.execute();
        }
    }
}