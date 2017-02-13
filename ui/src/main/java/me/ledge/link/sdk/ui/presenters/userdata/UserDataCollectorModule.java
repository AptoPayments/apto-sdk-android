package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.DataPointList;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.sdk.LedgeLinkSdk;
import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import me.ledge.link.sdk.ui.activities.userdata.MonthlyIncomeActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import me.ledge.link.sdk.ui.storages.SharedPreferencesStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends LedgeBaseModule implements PhoneVerificationDelegate,
        EmailVerificationDelegate, IdentityVerificationDelegate, AddressDelegate,
        AnnualIncomeDelegate, MonthlyIncomeDelegate, CreditScoreDelegate, PersonalInformationDelegate {

    private static UserDataCollectorModule instance;
    public Command onFinish;
    public Command onBack;
    private boolean isPhoneVerificationEnabled = true;

    public static synchronized  UserDataCollectorModule getInstance(Activity activity) {
        if (instance == null) {
            instance = new UserDataCollectorModule(activity);
        }
        return instance;
    }

    private UserDataCollectorModule(Activity activity) {
        super(activity);
        LedgeLinkSdk.getResponseHandler().subscribe(this);
    }

    @Override
    public void initialModuleSetup() {
        // TODO: read pos_mode
        String userToken = SharedPreferencesStorage.getUserToken(super.getActivity());
        if(userToken != null) {
            LedgeLinkUi.getApiWrapper().setBearerToken(userToken);
            LedgeLinkUi.getCurrentUser();
        }
        else {
            startActivity(PersonalInformationActivity.class);
        }
    }

    /**
     * Called when the get current user info response has been received.
     * @param userInfo API response.
     */
    @Subscribe
    public void handleResponse(DataPointList userInfo) {
        UserStorage.getInstance().setUserData(userInfo);
        // TODO: parse datapoints and compare with required ones in config
        startActivity(IdentityVerificationActivity.class);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        startActivity(PersonalInformationActivity.class);
    }

    @Override
    public void phoneVerificationSucceeded(DataPointVo phone) {
        if(phone.getVerification().getAlternateEmailCredentials() != null) {
            startActivity(EmailVerificationActivity.class);
        }
        else {
            startActivity(AddressActivity.class);
        }
    }

    @Override
    public void phoneVerificationOnBackPressed() {
        startActivity(PersonalInformationActivity.class);
    }

    @Override
    public void emailVerificationSucceeded() {
        onFinish.execute();
    }

    @Override
    public void emailOnBackPressed() {
        startActivity(PhoneVerificationActivity.class);
    }

    @Override
    public void identityVerificationSucceeded() {
        onFinish.execute();
    }

    @Override
    public void identityVerificationOnBackPressed() {
        startActivity(CreditScoreActivity.class);
    }

    @Override
    public void addressStored() {
        startActivity(AnnualIncomeActivity.class);
    }

    @Override
    public void addressOnBackPressed() {
        if(isPhoneVerificationEnabled) {
            startActivity(PhoneVerificationActivity.class);
        }
        else {
            startActivity(PersonalInformationActivity.class);
        }
    }

    @Override
    public void annualIncomeStored() {
        startActivity(MonthlyIncomeActivity.class);
    }

    @Override
    public void annualIncomeOnBackPressed() {
        startActivity(AddressActivity.class);
    }

    @Override
    public void monthlyIncomeStored() {
        startActivity(CreditScoreActivity.class);
    }

    @Override
    public void monthlyIncomeOnBackPressed() {
        startActivity(AnnualIncomeActivity.class);
    }

    @Override
    public void creditScoreStored() {
        startActivity(IdentityVerificationActivity.class);
    }

    @Override
    public void creditScoreOnBackPressed() {
        startActivity(MonthlyIncomeActivity.class);
    }

    @Override
    public void personalInformationStored() {
        // TODO: start phone verification only if enabled
        if(isPhoneVerificationEnabled) {
            startActivity(PhoneVerificationActivity.class);
        }
        else {
            startActivity(AddressActivity.class);
        }
    }

    @Override
    public void personalInformationOnBackPressed() {
        onBack.execute();
    }
}