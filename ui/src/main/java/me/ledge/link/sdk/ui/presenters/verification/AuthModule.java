package me.ledge.link.sdk.ui.presenters.verification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.datapoints.Birthdate;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.Email;
import me.ledge.link.api.vos.datapoints.VerificationVo;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.api.vos.responses.ApiErrorVo;
import me.ledge.link.api.vos.responses.SessionExpiredErrorVo;
import me.ledge.link.api.vos.responses.users.LoginUserResponseVo;
import me.ledge.link.api.vos.responses.verifications.BaseVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationResponseVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.PhoneActivity;
import me.ledge.link.sdk.ui.activities.verification.BirthdateVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.presenters.userdata.PhoneDelegate;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.workflow.Command;
import me.ledge.link.sdk.ui.workflow.LedgeBaseModule;

public class AuthModule extends LedgeBaseModule implements PhoneDelegate,
        PhoneVerificationDelegate, EmailVerificationDelegate, BirthdateVerificationDelegate {

    private static AuthModule instance;
    public Command onNewUserWithVerifiedPrimaryCredential;
    public Command onExistingUser;

    private ProgressDialog mProgressDialog;
    private DataPointList mInitialUserData;
    private AuthModuleConfig mConfig;

    private AuthModule(Activity activity, DataPointList initialUserData, AuthModuleConfig config) {
        super(activity);
        if (initialUserData == null) {
            mInitialUserData = new DataPointList();
        } else {
            mInitialUserData = initialUserData;
        }
        mConfig = config;
        SharedPreferencesStorage.storeCredentials(getActivity(), mConfig.primaryCredentialType.getType(), mConfig.secondaryCredentialType.getType());
    }

    public static synchronized AuthModule getInstance(Activity activity, DataPointList initialUserData, AuthModuleConfig config) {
        if (instance == null) {
            instance = new AuthModule(activity, initialUserData, config);
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
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
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
        showLoading(false);
        if (response != null) {
            storeToken(response.user_token);
            getCurrentUserOrContinue(ConfigStorage.getInstance().getPOSMode(), true);
        }
    }

    /**
     * Called when an API error has been received.
     *
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        showLoading(false);
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
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
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        LedgeLinkUi.clearUserToken(getActivity());
        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
        stopModule();
    }

    @Override
    public void phoneStored() {
        startActivity(PhoneVerificationActivity.class);
    }

    @Override
    public void phoneOnBackPressed() {
        super.onBack.execute();
    }

    @Override
    public void phoneVerificationSucceeded(VerificationResponseVo verification) {
        BaseVerificationResponseVo secondaryCredential = verification.secondary_credential;
        if (secondaryCredential == null) {
            onNewUserWithVerifiedPrimaryCredential.execute();
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
    public void emailVerificationSucceeded() {
        loginUser();
    }

    @Override
    public void emailOnBackPressed() {
        startPrimaryCredentialActivity();
    }

    @Override
    public void birthdateSucceeded() {
        loginUser();
    }

    @Override
    public void birthdateOnBackPressed() {
        startPrimaryCredentialActivity();
    }

    private void startPrimaryCredentialActivity() {
        if (mConfig.primaryCredentialType.equals(DataPointVo.DataPointType.Phone)) {
            startActivity(PhoneActivity.class);
        } else {
            Toast.makeText(getActivity(), "Configured primary credential is not supported!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser() {
        showLoading(true);
        LedgeLinkSdk.getResponseHandler().unsubscribe(this);
        LedgeLinkSdk.getResponseHandler().subscribe(this);
        LedgeLinkUi.loginUser(getLoginData());
    }

    private void storeToken(String token) {
        UserStorage.getInstance().setBearerToken(token);
        SharedPreferencesStorage.storeUserToken(getActivity(), token);
        SharedPreferencesStorage.storeCredentials(getActivity(), mConfig.primaryCredentialType.getType(), mConfig.secondaryCredentialType.getType());
    }

    private void getCurrentUserOrContinue(boolean isPOSMode, boolean validateUserToken) {
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity(), isPOSMode);
        if (!isPOSMode && userToken != null) {
            LedgeLinkUi.getApiWrapper().setBearerToken(userToken);
            LedgeLinkUi.getCurrentUser(validateUserToken);
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

    private void showLoading(boolean show) {
        if (mProgressDialog == null) {
            return;
        }
        if (show) {
            mProgressDialog = ProgressDialog.show(getActivity(), null, null);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.include_rl_loading_transparent);
        } else {
            mProgressDialog.dismiss();
        }
    }

    private void stopModule() {
        if (super.onFinish != null) {
            super.onFinish.execute();
        }
    }
}