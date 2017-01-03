package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.presenters.link.LinkModule;
import me.ledge.link.sdk.ui.presenters.offers.OffersListModule;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends LedgeBaseModule implements PhoneVerificationDelegate, EmailVerificationDelegate {

    public UserDataCollectorModule(Activity activity) {
        super(activity);
    }

    @Override
    public void initialModuleSetup() {
        startActivity(PersonalInformationActivity.class);
    }

    @Override
    public void onClose() {
        startModule(new LinkModule(this.getActivity()));
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
    public void emailVerificationSucceeded() {
        startModule(new OffersListModule(this.getActivity()));
    }
}
