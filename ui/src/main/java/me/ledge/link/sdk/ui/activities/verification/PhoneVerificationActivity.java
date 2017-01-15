package me.ledge.link.sdk.ui.activities.verification;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.UserDataActivity;
import me.ledge.link.sdk.ui.models.verification.PhoneVerificationModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationPresenter;
import me.ledge.link.sdk.ui.views.verification.PhoneVerificationView;

/**
 * Wires up the MVP pattern for the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationActivity
        extends UserDataActivity<PhoneVerificationModel, PhoneVerificationView, PhoneVerificationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected PhoneVerificationView createView() {
        return (PhoneVerificationView) View.inflate(this, R.layout.act_phone_verification, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PhoneVerificationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PhoneVerificationDelegate) {
            return new PhoneVerificationPresenter(this, (PhoneVerificationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement PhoneVerificationDelegate!");
        }
    }
}
