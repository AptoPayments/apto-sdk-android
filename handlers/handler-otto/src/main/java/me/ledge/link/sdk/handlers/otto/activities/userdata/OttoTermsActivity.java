package me.ledge.link.sdk.handlers.otto.activities.userdata;

import me.ledge.link.sdk.handlers.otto.presenters.userdata.OttoTermsPresenter;
import me.ledge.link.sdk.ui.activities.userdata.TermsActivity;
import me.ledge.link.sdk.ui.presenters.userdata.TermsPresenter;

/**
 * TODO: Class documentation.
 * @author Wijnand
 */
public class OttoTermsActivity extends TermsActivity {

    /** {@inheritDoc} */
    @Override
    protected TermsPresenter createPresenter() {
        return new OttoTermsPresenter(this);
    }
}
