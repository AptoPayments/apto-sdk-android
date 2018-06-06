package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.Menu;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.IdentityVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.IdentityVerificationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.IdentityVerificationView;

/**
 * Wires up the MVP pattern for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationActivity
        extends MvpActivity<IdentityVerificationModel, IdentityVerificationView, IdentityVerificationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationView createView() {
        return (IdentityVerificationView) View.inflate(this, R.layout.act_id_verification, null);
    }

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof IdentityVerificationDelegate) {
            return new IdentityVerificationPresenter(this, (IdentityVerificationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement IdentityVerificationDelegate!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.mDisclaimersShownCounter = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do not show next button for this screen
        return true;
    }
}
