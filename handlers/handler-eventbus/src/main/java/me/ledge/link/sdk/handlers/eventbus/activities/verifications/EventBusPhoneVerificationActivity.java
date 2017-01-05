package me.ledge.link.sdk.handlers.eventbus.activities.verifications;

import me.ledge.link.sdk.handlers.eventbus.presenters.verifications.EventBusPhoneVerificationPresenter;
import me.ledge.link.sdk.ui.activities.verification.PhoneVerificationActivity;
import me.ledge.link.sdk.ui.presenters.userdata.PersonalInformationPresenter;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationPresenter;


/**
 * An {@link PhoneVerificationActivity} that uses the {@link EventBus} to receive API data.
 * @author Adrian
 */
public class EventBusPhoneVerificationActivity extends PhoneVerificationActivity {

    /** {@inheritDoc} */
    @Override
    protected PhoneVerificationPresenter createPresenter() {
        return new EventBusPhoneVerificationPresenter(this, new PersonalInformationPresenter(null));
    }
}
