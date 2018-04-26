package com.shiftpayments.link.sdk.ui.activities.verification;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.userdata.UserDataActivity;
import com.shiftpayments.link.sdk.ui.models.verification.BirthdateVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.BirthdateVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.BirthdateVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.verification.BirthdateVerificationView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.BirthdateVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.verification.BirthdateVerificationPresenter;

/**
 * Wires up the MVP pattern for the birthdate screen.
 * @author Adrian
 */
public class BirthdateVerificationActivity
        extends UserDataActivity<BirthdateVerificationModel, BirthdateVerificationView, BirthdateVerificationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected BirthdateVerificationView createView() {
        return (BirthdateVerificationView) View.inflate(this, R.layout.act_birthdate, null);
    }

    /** {@inheritDoc} */
    @Override
    protected BirthdateVerificationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof BirthdateVerificationDelegate) {
            return new BirthdateVerificationPresenter(this, (BirthdateVerificationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement BirthdateVerificationDelegate!");
        }
    }
}
