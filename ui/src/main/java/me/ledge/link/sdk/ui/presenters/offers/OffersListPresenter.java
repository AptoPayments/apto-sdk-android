package me.ledge.link.sdk.ui.presenters.offers;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import me.ledge.common.utils.PagedList;
import me.ledge.link.api.utils.LoanApplicationStatus;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.loanapplication.IntermediateLoanApplicationActivity;
import me.ledge.link.sdk.ui.adapters.OffersListRecyclerAdapter;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.offers.OfferSummaryView;
import me.ledge.link.sdk.ui.views.offers.OffersListView;

/**
 * Concrete {@link Presenter} for the offers list screen.
 * TODO: Some loading indicator when offers are being loaded.
 * TODO: Not reload offers when they are already present.
 * @author Wijnand
 */
public class OffersListPresenter
        extends ActivityPresenter<OffersListModel, OffersListView>
        implements Presenter<OffersListModel, OffersListView>, OffersListView.ViewListener, OfferSummaryView.ViewListener {

    private OffersListRecyclerAdapter mAdapter;

    /**
     * Creates a new {@link OffersListPresenter} instance.
     * @param activity Activity.
     */
    public OffersListPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    public OffersListModel createModel() {
        OffersListModel model = new OffersListModel();
        model.setBaseData(UserStorage.getInstance().getUserData());

        return model;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(OffersListView view) {
        super.attachView(view);

        mAdapter = new OffersListRecyclerAdapter();
        mAdapter.setViewListener(this);
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
    public void updateClickedHandler() {
        // Start the second step in the process.
        // TODO: specify this differently? There is no guaranteed order! yet?
        startActivity(LedgeLinkUi.getProcessOrder().get(1));
    }

    /** {@inheritDoc} */
    @Override
    public void applyClickHandler(OfferSummaryModel offer) {
        if (offer != null) {
            LedgeLinkUi.createLoanApplication(offer.getOfferId());
        }
    }

    /**
     * Adds a list of offers and displays them.
     * @param rawOffers List of offers.
     * @param offerRequestId Offer request ID.
     * @param complete Whether the list is complete.
     */
    public void addOffers(OfferVo[] rawOffers, int offerRequestId, boolean complete) {
        LoanStorage storage = LoanStorage.getInstance();

        storage.setOfferRequestId(offerRequestId);
        storage.addOffers(mActivity.getResources(), rawOffers, complete, LedgeLinkUi.getImageLoader());

        PagedList<OfferSummaryModel> offers = storage.getOffers();
        mAdapter.updateList(offers);
        mView.showEmptyCase(offers.isComplete() && (offers.getList() == null || offers.getList().size() <= 0));
    }

    /**
     * Shows a loan application screen based on the API response.
     * @param response API reponse.
     */
    public void showLoanApplicationScreen(LoanApplicationDetailsResponseVo response) {
        mView.showLoading(false);
        LoanStorage.getInstance().setCurrentLoanApplication(response);

        switch (response.status) {
            case LoanApplicationStatus.APPLICATION_REJECTED:
            case LoanApplicationStatus.PENDING_LENDER_ACTION:
                startActivity(IntermediateLoanApplicationActivity.class);
                break;
            case LoanApplicationStatus.APPLICATION_RECEIVED:
            case LoanApplicationStatus.PENDING_BORROWER_ACTION:
            case LoanApplicationStatus.LENDER_REJECTED:
            case LoanApplicationStatus.BORROWER_REJECTED:
            case LoanApplicationStatus.LOAN_APPROVED:
            default:
                Toast.makeText(mActivity, "Screen not yet implemented.", Toast.LENGTH_LONG).show();
                break;
        }
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
