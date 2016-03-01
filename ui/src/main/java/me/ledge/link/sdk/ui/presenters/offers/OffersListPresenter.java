package me.ledge.link.sdk.ui.presenters.offers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.offers.OffersListView;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Presenter} for the offers list screen.
 * @author Wijnand
 */
public class OffersListPresenter
        extends ActivityPresenter<OffersListModel, OffersListView>
        implements Presenter<OffersListModel, OffersListView> {

    /**
     * Creates a new {@link OffersListPresenter} instance.
     * @param activity Activity.
     */
    public OffersListPresenter(AppCompatActivity activity) {
        super(activity);
        getIntentData();
    }

    /**
     * Retrieves the bearer token from the start {@link Intent}.
     */
    private void getIntentData() {
        if (mActivity == null || mActivity.getIntent() == null) {
            return;
        }

        Intent intent = mActivity.getIntent();

        UserDataVo baseData = intent.getParcelableExtra(UserDataVo.USER_DATA_KEY);
        mModel.setBaseData(baseData);
    }

    /** {@inheritDoc} */
    @Override
    public OffersListModel createModel() {
        return new OffersListModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(OffersListView view) {
        super.attachView(view);

        // Fetch offers.
        LedgeLinkUi.getInitialOffers(mModel.getInitialOffersRequest());
    }
}
