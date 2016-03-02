package me.ledge.link.sdk.ui.presenters.offers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.responses.offers.OfferWrapperVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.offers.OfferSummaryView;
import me.ledge.link.sdk.ui.views.offers.OffersListView;
import me.ledge.link.sdk.ui.vos.UserDataVo;

/**
 * Concrete {@link Presenter} for the offers list screen.
 * TODO: Some loading indicator when offers are being loaded.
 * TODO: Not reload offers when they are already present.
 * @author Wijnand
 */
public class OffersListPresenter
        extends ActivityPresenter<OffersListModel, OffersListView>
        implements Presenter<OffersListModel, OffersListView> {

    private PagedListRecyclerAdapter<OfferSummaryModel, OfferSummaryView> mAdapter;

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

        mAdapter = new PagedListRecyclerAdapter<>(R.layout.cv_loan_offer);
        mView.setAdapter(mAdapter);

        // Fetch offers.
        LedgeLinkUi.getInitialOffers(mModel.getInitialOffersRequest());
    }

    /** {@inheritDoc} */
    @Override
    protected Intent getStartIntent(Class activity) {
        Intent intent = super.getStartIntent(activity);
        intent.putExtra(UserDataVo.USER_DATA_KEY, mModel.getBaseData());

        return intent;
    }

    /**
     * Adds a list of offers and displays them.
     * @param offers List of offers.
     * @param offerRequestId Offer request ID.
     * @param complete Whether the list is complete.
     */
    public void addOffers(OfferWrapperVo[] offers, int offerRequestId, boolean complete) {
        mModel.setOfferRequestId(offerRequestId);
        mModel.addOffers(mActivity.getResources(), offers, complete);

        mAdapter.updateList(mModel.getOffers());
    }

    /**
     * Deals with an API error.
     * @param error API error.
     */
    public void setApiError(ApiErrorVo error) {
        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
    }
}
