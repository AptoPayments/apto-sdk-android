package com.shiftpayments.link.sdk.ui.activities.verification;

import android.view.Menu;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.verification.EmailVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.EmailVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.verification.VerificationView;

/**
 * Wires up the MVP pattern for the email verification screen.
 * @author Adrian
 */
public class EmailVerificationActivity
        extends MvpActivity<EmailVerificationModel, VerificationView, EmailVerificationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected VerificationView createView() {
        return (VerificationView) View.inflate(this, R.layout.act_datapoint_verification, null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do not show next button for this screen
        return true;
    }
}
