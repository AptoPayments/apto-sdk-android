package me.ledge.link.sdk.handlers.eventbus.activities.verifications;

import me.ledge.link.sdk.handlers.eventbus.presenters.verifications.EventBusEmailVerificationPresenter;
import me.ledge.link.sdk.ui.activities.verification.EmailVerificationActivity;
import me.ledge.link.sdk.ui.presenters.userdata.PersonalInformationPresenter;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationPresenter;


/**
 * An {@link EmailVerificationActivity} that uses the {@link EventBus} to receive API data.
 * @author Adrian
 */
public class EventBusEmailVerificationActivity extends EmailVerificationActivity {

    /** {@inheritDoc} */
    @Override
    protected EmailVerificationPresenter createPresenter() {
        return new EventBusEmailVerificationPresenter(this, new PersonalInformationPresenter(null));
    }
}
