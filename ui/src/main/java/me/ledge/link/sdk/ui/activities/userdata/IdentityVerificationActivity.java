package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.IdentityVerificationModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.IdentityVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;
import me.ledge.link.sdk.ui.views.userdata.IdentityVerificationView;

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
    protected void onResume() {
        super.onResume();
        mPresenter.mDisclaimersShownCounter = 0;
    }
}
