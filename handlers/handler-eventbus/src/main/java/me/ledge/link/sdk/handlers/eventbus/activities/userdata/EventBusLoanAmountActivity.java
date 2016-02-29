package me.ledge.link.sdk.handlers.eventbus.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.EventBusLoanAmountPresenter;
import me.ledge.link.sdk.ui.activities.userdata.LoanAmountActivity;
import me.ledge.link.sdk.ui.presenters.userdata.LoanAmountPresenter;

/**
 * An {@link LoanAmountActivity} that uses the {@link EventBus} to receive API data.
 * @author wijnand
 */
public class EventBusLoanAmountActivity extends LoanAmountActivity {

    /** {@inheritDoc} */
    @Override
    protected LoanAmountPresenter createPresenter() {
        return new EventBusLoanAmountPresenter(this);
    }
}
