package me.ledge.link.sdk.sdk.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.MvpActivity;
import me.ledge.link.sdk.sdk.models.userdata.TermsModel;
import me.ledge.link.sdk.sdk.presenters.userdata.TermsPresenter;
import me.ledge.link.sdk.sdk.views.userdata.TermsView;

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
