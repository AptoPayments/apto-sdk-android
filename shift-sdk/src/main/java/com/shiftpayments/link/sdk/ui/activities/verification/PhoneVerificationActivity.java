package com.shiftpayments.link.sdk.ui.activities.verification;

import android.view.Menu;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.verification.PhoneVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.PhoneVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.verification.VerificationView;

/**
 * Wires up the MVP pattern for the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationActivity
        extends MvpActivity<PhoneVerificationModel, VerificationView, PhoneVerificationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected VerificationView createView() {
        return (VerificationView) View.inflate(this, R.layout.act_datapoint_verification, null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do not show next button for this screen
        return true;
    }
}
