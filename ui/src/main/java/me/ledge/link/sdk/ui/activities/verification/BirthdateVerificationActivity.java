package me.ledge.link.sdk.ui.activities.verification;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.UserDataActivity;
import me.ledge.link.sdk.ui.models.verification.BirthdateVerificationModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.verification.BirthdateVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.BirthdateVerificationPresenter;
import me.ledge.link.sdk.ui.views.verification.BirthdateVerificationView;

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
