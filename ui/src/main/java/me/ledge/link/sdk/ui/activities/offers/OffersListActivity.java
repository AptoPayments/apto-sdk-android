package me.ledge.link.sdk.ui.activities.offers;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.workflow.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.MvpActivity;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.loanapplication.LoanApplicationModule;
import me.ledge.link.sdk.ui.presenters.offers.OffersListDelegate;
import me.ledge.link.sdk.ui.presenters.offers.OffersListPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.views.offers.OffersBaseView;
import me.ledge.link.sdk.ui.views.offers.OffersCarouselView;
import me.ledge.link.sdk.ui.views.offers.OffersListView;

/**
 * Wires up the MVP pattern for the offers list screen.
 * @author Wijnand
 */
public class OffersListActivity extends MvpActivity<OffersListModel, OffersBaseView, OffersListPresenter> {

    /** {@inheritDoc} */
    @Override
    protected OffersBaseView createView() {
        if(((LoanApplicationModule) ModuleManager.getInstance().getCurrentModule()).mOffersListStyle.equals(ConfigStorage.OffersListStyle.carousel)) {
            return (OffersCarouselView) View.inflate(this, R.layout.act_offers_carousel, null);
        }
        else {
            return (OffersListView) View.inflate(this, R.layout.act_offers_list, null);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected OffersListPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof OffersListDelegate) {
            return new OffersListPresenter(this, (OffersListDelegate) delegate);
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
