package me.ledge.link.sdk.ui.activities.offers;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.offers.OffersCarouselModel;
import me.ledge.link.sdk.ui.presenters.offers.OffersCarouselPresenter;
import me.ledge.link.sdk.ui.presenters.offers.OffersListDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.offers.OffersCarouselView;

/**
 * Wires up the MVP pattern for the offers carousel screen.
 * @author Adrian
 */
public class OffersCarouselActivity extends MvpActivity<OffersCarouselModel, OffersCarouselView, OffersCarouselPresenter> {

    /** {@inheritDoc} */
    @Override
    protected OffersCarouselView createView() {
        return (OffersCarouselView) View.inflate(this, R.layout.act_offers_carousel, null);
    }

    /** {@inheritDoc} */
    @Override
    protected OffersCarouselPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof OffersListDelegate) {
            return new OffersCarouselPresenter(this, (OffersListDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement OffersListDelegate!");
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_offers_list, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mPresenter.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
