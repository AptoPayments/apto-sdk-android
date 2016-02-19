package me.ledge.link.sdk.ui.activities.userdata;

import me.ledge.link.sdk.ui.presenters.userdata.EventBusIdentyVerificationPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;

/**
 * An {@link IdentityVerificationActivity} that uses the {@link EventBus} to receive API data.
 * @author Wijnand
 */
public class EventBusIdentityVerificationActivity extends IdentityVerificationActivity {

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationPresenter createPresenter() {
        return new EventBusIdentyVerificationPresenter(this);
    }
}
