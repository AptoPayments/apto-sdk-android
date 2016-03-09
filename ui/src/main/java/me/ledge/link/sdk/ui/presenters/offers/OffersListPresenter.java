package me.ledge.link.sdk.ui.presenters.offers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.ledge.common.adapters.recyclerview.PagedListRecyclerAdapter;
import me.ledge.common.utils.PagedList;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
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
        implements Presenter<OffersListModel, OffersListView>, OffersListView.ViewListener {

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
        return new OffersListModel(LedgeLinkUi.getImageLoader());
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(OffersListView view) {
        super.attachView(view);

        mAdapter = new PagedListRecyclerAdapter<>(R.layout.cv_loan_offer);
        mView.setAdapter(mAdapter);
        mView.setListener(this);

        // Fetch offers.
        InitialOffersRequestVo requestData = mModel.getInitialOffersRequest();
        requestData.rows = 25;
        LedgeLinkUi.getInitialOffers(requestData);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setAdapter(null);
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    protected Intent getStartIntent(Class activity) {
        Intent intent = super.getStartIntent(activity);
        intent.putExtra(UserDataVo.USER_DATA_KEY, mModel.getBaseData());

        return intent;
    }

    /** {@inheritDoc} */
    @Override
    public void updateClickedHandler() {
        // Start the second step in the process.
        startActivity(LedgeLinkUi.getProcessOrder().get(1)); // TODO: specify this differently? There is no guaranteed order! yet?
    }

    /**
     * Adds a list of offers and displays them.
     * @param rawOffers List of offers.
     * @param offerRequestId Offer request ID.
     * @param complete Whether the list is complete.
     */
    public void addOffers(OfferVo[] rawOffers, int offerRequestId, boolean complete) {
        mModel.setOfferRequestId(offerRequestId);
        mModel.addOffers(mActivity.getResources(), rawOffers, complete);

        PagedList<OfferSummaryModel> offers = mModel.getOffers();
        mAdapter.updateList(offers);
        mView.showEmptyCase(offers.isComplete() && (offers.getList() == null || offers.getList().size() <= 0));
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
