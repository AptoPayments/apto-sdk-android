package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.content.Intent;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.LedgeBaseModule;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.activities.userdata.TermsActivity;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;

/**
 * Created by adrian on 29/12/2016.
 */

public class UserDataCollectorModule extends LedgeBaseModule implements PhoneVerificationDelegate, EmailVerificationDelegate {

    private Activity mActivity;

    public UserDataCollectorModule(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void initialModuleSetup() {
        startActivity(PersonalInformationActivity.class);
    }

    @Override
    public void onClose() {
        startActivity(TermsActivity.class);
    }

    @Override
    public void onBack() {
        mActivity.onBackPressed();
    }

    @Override
    public void onFinish(int result) {
        mActivity.setResult(result);
    }

    /**
     * Starts another activity.
     * @param activity The Activity to start.
     */
    @Override
    public void startActivity(Class activity) {
        if (activity != null) {
            mActivity.startActivity(getStartIntent(activity));
        }
        mActivity.finish();
    }

    /**
     * @param activity The Activity to start.
     * @return The {@link Intent} to use to start the next Activity.
     */
    protected Intent getStartIntent(Class activity) {
        return new Intent(mActivity, activity);
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
        startActivity(OffersListActivity.class);
    }
}
