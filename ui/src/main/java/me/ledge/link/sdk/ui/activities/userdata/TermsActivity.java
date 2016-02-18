package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.userdata.TermsModel;
import me.ledge.link.sdk.ui.presenters.userdata.TermsPresenter;
import me.ledge.link.sdk.ui.views.userdata.TermsView;

/**
 * Wires up the MVP pattern for the terms and conditions screen.
 * @author Wijnand
 */
public class TermsActivity extends MvpActivity<TermsModel, TermsView, TermsPresenter> {

    /** {@inheritDoc} */
    @Override
    protected TermsView createView() {
        return (TermsView) View.inflate(this, R.layout.act_terms_conditions, null);
    }

    /** {@inheritDoc} */
    @Override
    protected TermsPresenter createPresenter() {
        return new TermsPresenter(this);
    }
}
