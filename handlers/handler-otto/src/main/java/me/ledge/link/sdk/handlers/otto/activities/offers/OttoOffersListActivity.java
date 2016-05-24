package me.ledge.link.sdk.handlers.otto.activities.offers;

import me.ledge.link.sdk.handlers.eventbus.presenters.offers.OttoOffersListPresenter;
import me.ledge.link.sdk.ui.activities.offers.OffersListActivity;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;

/**
 * An {@link OffersListActivity} that uses the Otto event bus to receive API data.
 * @author wijnand
 */
public class OttoOffersListActivity extends OffersListActivity {

    /** {@inheritDoc} */
    @Override
    protected OffersListPresenter createPresenter() {
        return new OttoOffersListPresenter(this);
    }
}
