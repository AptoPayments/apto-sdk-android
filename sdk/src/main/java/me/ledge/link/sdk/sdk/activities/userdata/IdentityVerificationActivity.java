package me.ledge.link.sdk.sdk.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.MvpActivity;
import me.ledge.link.sdk.sdk.models.userdata.IdentityVerificationModel;
import me.ledge.link.sdk.sdk.presenters.userdata.IdentityVerificationPresenter;
import me.ledge.link.sdk.sdk.views.userdata.IdentityVerificationView;

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
    protected IdentityVerificationPresenter createPresenter() {
        return new IdentityVerificationPresenter(this);
    }
}
