package me.ledge.link.sdk.ui.activities.offers;

import android.view.View;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;
import me.ledge.link.sdk.ui.views.offers.OffersListView;

/**
 * Wires up the MVP pattern for the offers list screen.
 * @author Wijnand
 */
public class OffersListActivity extends MvpActivity<OffersListModel, OffersListView, OffersListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected OffersListView createView() {
        return (OffersListView) View.inflate(this, R.layout.act_offers_list, null);
    }

    /** {@inheritDoc} */
    @Override
    protected OffersListPresenter createPresenter() {
        return new OffersListPresenter(this);
    }
}
