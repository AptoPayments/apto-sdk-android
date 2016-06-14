package me.ledge.link.sdk.handlers.eventbus.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.EventBusAddressPresenter;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.presenters.userdata.AddressPresenter;

/**
 * An {@link AddressActivity} that uses the {@link EventBus} to receive API data.
 * @author Wijnand
 */
public class EventBusAddressActivity extends AddressActivity {

    /** {@inheritDoc} */
    @Override
    protected AddressPresenter createPresenter() {
        return new EventBusAddressPresenter(this);
    }
}
