package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.Command;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.activities.userdata.CreditScoreActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends LedgeBaseModule implements PhoneVerificationDelegate,
        EmailVerificationDelegate, IdentityVerificationDelegate {

    private static UserDataCollectorModule instance;
    public Command onFinish;

    public static synchronized  UserDataCollectorModule getInstance(Activity activity) {
        if (instance == null) {
            instance = new UserDataCollectorModule(activity);
        }
        return instance;
    }

    private UserDataCollectorModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
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
    public void phoneOnBackPressed() {
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
}
