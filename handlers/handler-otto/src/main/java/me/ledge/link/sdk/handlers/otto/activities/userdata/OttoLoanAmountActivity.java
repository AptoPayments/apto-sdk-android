package me.ledge.link.sdk.handlers.otto.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.OttoLoanAmountPresenter;
import me.ledge.link.sdk.ui.activities.userdata.LoanAmountActivity;
import me.ledge.link.sdk.ui.presenters.userdata.LoanAmountPresenter;

/**
 * An {@link LoanAmountActivity} that uses the Otto event bus to receive API data.
 * @author wijnand
 */
public class OttoLoanAmountActivity extends LoanAmountActivity {

    /** {@inheritDoc} */
    @Override
    protected LoanAmountPresenter createPresenter() {
        return new OttoLoanAmountPresenter(this);
    }
}
