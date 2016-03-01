package me.ledge.link.sdk.handlers.eventbus.activities.offers;

import me.ledge.link.sdk.handlers.eventbus.presenters.offers.EventBusOffersListPresenter;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;

/**
 * An {@link OffersListActivity} that uses the {@link EventBus} to receive API data.
 * @author wijnand
 */
public class EventBusOffersListActivity extends OffersListActivity {

    /** {@inheritDoc} */
    @Override
    protected OffersListPresenter createPresenter() {
        return new EventBusOffersListPresenter(this);
    }
}
