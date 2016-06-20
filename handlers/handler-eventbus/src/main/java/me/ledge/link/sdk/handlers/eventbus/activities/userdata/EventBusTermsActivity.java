package me.ledge.link.sdk.handlers.eventbus.activities.userdata;

import me.ledge.link.sdk.handlers.eventbus.presenters.userdata.EventBusTermsPresenter;
import me.ledge.link.sdk.ui.activities.userdata.TermsActivity;
import me.ledge.link.sdk.ui.presenters.userdata.TermsPresenter;

/**
 * TODO: Class documentation.
 * @author Wijnand
 */
public class EventBusTermsActivity extends TermsActivity {

    /** {@inheritDoc} */
    @Override
    protected TermsPresenter createPresenter() {
        return new EventBusTermsPresenter(this);
    }
}
