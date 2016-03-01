package me.ledge.link.sdk.ui.presenters.offers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.offers.OfferWrapperVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
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

    /**
     * Adds a list of offers and displays them.
     * @param offers List of offers.
     * @param offerRequestId Offer request ID.
     * @param complete Whether the list is complete.
     */
    public void addOffers(OfferWrapperVo[] offers, int offerRequestId, boolean complete) {
        mModel.setOfferRequestId(offerRequestId);
        mModel.addOffers(offers);
    }

    /**
     * Deals with an API error.
     * @param error API error.
     */
    public void setApiError(ApiErrorVo error) {
//        mView.showLoading(false);

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }
}
