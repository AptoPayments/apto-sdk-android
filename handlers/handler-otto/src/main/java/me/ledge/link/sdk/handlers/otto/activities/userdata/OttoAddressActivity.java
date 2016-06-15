package me.ledge.link.sdk.handlers.otto.activities.userdata;

import me.ledge.link.sdk.handlers.otto.presenters.userdata.OttoAddressPresenter;
import me.ledge.link.sdk.ui.activities.userdata.AddressActivity;
import me.ledge.link.sdk.ui.presenters.userdata.AddressPresenter;

/**
 * An {@link AddressActivity} that uses the Otto event bus to receive API data.
 * @author Wijnand
 */
public class OttoAddressActivity extends AddressActivity {

    /** {@inheritDoc} */
    @Override
    protected AddressPresenter createPresenter() {
        return new OttoAddressPresenter(this);
    }
}
