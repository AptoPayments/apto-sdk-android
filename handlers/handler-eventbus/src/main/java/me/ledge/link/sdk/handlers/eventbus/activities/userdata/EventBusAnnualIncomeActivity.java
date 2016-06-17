package me.ledge.link.sdk.handlers.eventbus.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.EventBusAnnualIncomePresenter;
import me.ledge.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import me.ledge.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;

/**
 * An {@link AnnualIncomeActivity} that uses the {@link EventBus} to receive API data.
 * @author Wijnand
 */
public class EventBusAnnualIncomeActivity extends AnnualIncomeActivity {

    /** {@inheritDoc} */
    @Override
    protected AnnualIncomePresenter createPresenter() {
        return new EventBusAnnualIncomePresenter(this);
    }
}
