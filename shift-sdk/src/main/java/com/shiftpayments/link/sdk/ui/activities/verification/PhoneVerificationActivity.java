package com.shiftpayments.link.sdk.ui.activities.verification;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.verification.PhoneVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.PhoneVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.verification.PhoneVerificationView;

/**
 * Wires up the MVP pattern for the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationActivity
        extends MvpActivity<PhoneVerificationModel, PhoneVerificationView, PhoneVerificationPresenter> {

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
