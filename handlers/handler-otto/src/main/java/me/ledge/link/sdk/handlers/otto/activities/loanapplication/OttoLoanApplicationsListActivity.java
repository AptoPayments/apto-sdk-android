package me.ledge.link.sdk.handlers.otto.activities.loanapplication;

import me.ledge.link.sdk.handlers.eventbus.presenters.loanapplication.OttoLoanApplicationsListPresenter;
import me.ledge.link.sdk.ui.activities.loanapplication.LoanApplicationsListActivity;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationsListPresenter;

/**
 * A {@link LoanApplicationsListActivity} that uses the Otto event bus to receive API data.
 * @author Wijnand
 */
public class OttoLoanApplicationsListActivity extends LoanApplicationsListActivity {

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationsListPresenter createPresenter() {
        return new OttoLoanApplicationsListPresenter(this);
    }
}
