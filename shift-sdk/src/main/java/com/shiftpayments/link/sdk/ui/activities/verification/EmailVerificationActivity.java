package com.shiftpayments.link.sdk.ui.activities.verification;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.verification.EmailVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.EmailVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.verification.EmailVerificationView;

/**
 * Wires up the MVP pattern for the email verification screen.
 * @author Adrian
 */
public class EmailVerificationActivity
        extends MvpActivity<EmailVerificationModel, EmailVerificationView, EmailVerificationPresenter> {

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
