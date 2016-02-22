package me.ledge.link.sdk.ui.presenters.offers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.offers.OffersListView;

/**
 * Concrete {@link Presenter} for the offers list screen.
 * @author Wijnand
 */
public class OffersListPresenter
        extends ActivityPresenter<OffersListModel, OffersListView>
        implements Presenter<OffersListModel, OffersListView> {

    public static final String BEARER_TOKEN_KEY = "me.ledge.link.sdk.ui.presenters.offers.BearerToken";

    /**
     * Creates a new {@link OffersListPresenter} instance.
     * @param activity Activity.
     */
    public OffersListPresenter(AppCompatActivity activity) {
        super(activity);
        getBearerToken();
    }

    /**
     * Retrieves the bearer token from the start {@link Intent}.
     */
    private void getBearerToken() {
        if (mActivity == null || mActivity.getIntent() == null) {
            return;
        }

        Intent intent = mActivity.getIntent();
        String token = intent.getStringExtra(BEARER_TOKEN_KEY);
        mModel.setBearerToken(token);
    }

    /** {@inheritDoc} */
    @Override
    public OffersListModel createModel() {
        return new OffersListModel();
    }
}
