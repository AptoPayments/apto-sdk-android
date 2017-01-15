package me.ledge.link.sdk.ui.activities.link;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.userdata.UserDataActivity;
import me.ledge.link.sdk.ui.models.link.TermsModel;
import me.ledge.link.sdk.ui.presenters.link.TermsDelegate;
import me.ledge.link.sdk.ui.presenters.link.TermsPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.userdata.TermsView;

/**
 * Wires up the MVP pattern for the terms and conditions screen.
 * @author Wijnand
 */
public class TermsActivity extends UserDataActivity<TermsModel, TermsView, TermsPresenter> {

    /** {@inheritDoc} */
    @Override
    protected TermsView createView() {
        return (TermsView) View.inflate(this, R.layout.act_terms_conditions, null);
    }

    /** {@inheritDoc} */
    @Override
    protected TermsPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof TermsDelegate) {
            return new TermsPresenter(this, (TermsDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement TermsDelegate!");
        }
    }
}
