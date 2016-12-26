package me.ledge.link.sdk.handlers.eventbus.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.EventBusPersonalInformationPresenter;
import me.ledge.link.sdk.ui.activities.userdata.PersonalInformationActivity;
import me.ledge.link.sdk.ui.presenters.userdata.PersonalInformationPresenter;


/**
 * An {@link PersonalInformationActivity} that uses the {@link EventBus} to receive API data.
 * @author Adrian
 */
public class EventBusPersonalInformationActivity extends PersonalInformationActivity {

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationPresenter createPresenter() {
        return new EventBusPersonalInformationPresenter(this);
    }
}
