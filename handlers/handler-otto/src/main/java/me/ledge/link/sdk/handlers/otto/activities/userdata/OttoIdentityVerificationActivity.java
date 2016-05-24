package me.ledge.link.sdk.handlers.otto.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.OttoIdentyVerificationPresenter;
import me.ledge.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import me.ledge.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;

/**
 * An {@link IdentityVerificationActivity} that uses the Otto event bus to receive API data.
 * @author Wijnand
 */
public class OttoIdentityVerificationActivity extends IdentityVerificationActivity {

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationPresenter createPresenter() {
        return new OttoIdentyVerificationPresenter(this);
    }
}
