package me.ledge.link.sdk.handlers.otto.activities.userdata;

import me.ledge.link.sdk.handlers.otto.presenters.userdata.OttoAnnualIncomePresenter;
import me.ledge.link.sdk.ui.activities.userdata.AnnualIncomeActivity;
import me.ledge.link.sdk.ui.presenters.userdata.AnnualIncomePresenter;

/**
 * An {@link AnnualIncomeActivity} that uses the Otto event bus to receive API data.
 * @author Wijnand
 */
public class OttoAnnualIncomeActivity extends AnnualIncomeActivity {

    /** {@inheritDoc} */
    @Override
    protected AnnualIncomePresenter createPresenter() {
        return new OttoAnnualIncomePresenter(this);
    }
}
