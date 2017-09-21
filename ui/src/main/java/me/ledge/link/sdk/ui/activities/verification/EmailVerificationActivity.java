package me.ledge.link.sdk.ui.activities.verification;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.UserDataActivity;
import me.ledge.link.sdk.ui.models.verification.EmailVerificationModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationPresenter;
import me.ledge.link.sdk.ui.views.verification.EmailVerificationView;

/**
 * Wires up the MVP pattern for the email verification screen.
 * @author Adrian
 */
public class EmailVerificationActivity
        extends UserDataActivity<EmailVerificationModel, EmailVerificationView, EmailVerificationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected EmailVerificationView createView() {
        return (EmailVerificationView) View.inflate(this, R.layout.act_email_verification, null);
    }

    /** {@inheritDoc} */
    @Override
    protected EmailVerificationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof EmailVerificationDelegate) {
            return new EmailVerificationPresenter(this, (EmailVerificationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement EmailVerificationDelegate!");
        }
    }
}
