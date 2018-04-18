package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.IdentityVerificationModel;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.IdentityVerificationDelegate;
import com.shift.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;
import com.shift.link.sdk.ui.views.userdata.IdentityVerificationView;

/**
 * Wires up the MVP pattern for the ID verification screen.
 * @author Wijnand
 */
public class IdentityVerificationActivity
        extends UserDataActivity<IdentityVerificationModel, IdentityVerificationView, IdentityVerificationPresenter> {

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
}
