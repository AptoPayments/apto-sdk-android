package me.ledge.link.sdk.handlers.eventbus.activities.loanapplication;

import me.ledge.link.sdk.handlers.eventbus.presenters.loanapplication.EventBusLoanApplicationsListPresenter;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationsListPresenter;

/**
 * A {@link LoanApplicationsListActivity} that uses the {@link EventBus} to receive API data.
 * @author Wijnand
 */
public class EventBusLoanApplicationsListActivity extends LoanApplicationsListActivity {

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationsListPresenter createPresenter() {
        return new EventBusLoanApplicationsListPresenter(this);
    }
}
