package me.ledge.link.sdk.handlers.eventbus.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.EventBusIdentityVerificationPresenter;
import me.ledge.link.sdk.ui.activities.userdata.IdentityVerificationActivity;
import me.ledge.link.sdk.ui.presenters.userdata.IdentityVerificationPresenter;

/**
 * An {@link IdentityVerificationActivity} that uses the {@link EventBus} to receive API data.
 * @author Wijnand
 */
public class EventBusIdentityVerificationActivity extends IdentityVerificationActivity {

    /** {@inheritDoc} */
    @Override
    protected IdentityVerificationPresenter createPresenter() {
        return new EventBusIdentityVerificationPresenter(this);
    }
}
