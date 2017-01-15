package me.ledge.link.sdk.ui.presenters.offers;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import me.ledge.common.fragments.dialogs.DualOptionDialogFragment;
import me.ledge.common.fragments.dialogs.NotificationDialogFragment;
import me.ledge.common.utils.PagedList;
import me.ledge.common.utils.web.ExternalSiteLauncher;
import me.ledge.link.api.utils.loanapplication.LoanApplicationMethod;
import me.ledge.link.api.utils.loanapplication.LoanApplicationStatus;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.api.vos.responses.offers.OfferVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.adapters.offers.OffersListRecyclerAdapter;
import me.ledge.link.sdk.ui.models.loanapplication.BigButtonModel;
import me.ledge.link.sdk.ui.models.offers.OfferSummaryModel;
import me.ledge.link.sdk.ui.models.offers.OffersListModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LinkStorage;
import me.ledge.link.sdk.ui.storages.LoanStorage;
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
        implements Presenter<OffersListModel, OffersListView>, OffersListView.ViewListener,
        OfferSummaryView.ViewListener, DualOptionDialogFragment.DialogListener {

    private LoanStorage mLoanStorage;
    private OffersListRecyclerAdapter mAdapter;
    private DualOptionDialogFragment mDialog;
    private NotificationDialogFragment mNotificationDialog;

    private OffersListDelegate mDelegate;
    /**
     * Creates a new {@link OffersListPresenter} instance.
     * @param activity Activity.
     */
    public OffersListPresenter(AppCompatActivity activity, OffersListDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    protected void init() {
        super.init();

        mLoanStorage = LoanStorage.getInstance();
        mDialog = new DualOptionDialogFragment();
        mNotificationDialog = new NotificationDialogFragment();
    }

    /**
     * Reloads the list of loan offers.
     */
    private void reloadOffers() {
        if (mView != null) {
            mView.showLoading(true);
        }

        clearAdapter();
        mLoanStorage.clearOffers();

        // Fetch offers.
        InitialOffersRequestVo requestData = mModel.getInitialOffersRequest();
        requestData.rows = 25;
        LedgeLinkUi.getInitialOffers(requestData);
    }

    /**
     * Clears the {@link OffersListRecyclerAdapter}.
     */
    private void clearAdapter() {
        if (mAdapter != null) {
            // Clear current adapter.
            mAdapter.setViewListener(null);
        }

        // Create new one.
        mAdapter = new OffersListRecyclerAdapter();
        mAdapter.setViewListener(this);

        if (mView != null) {
            mView.setAdapter(mAdapter);
        }
    }

    private void showInfo() {
        new ExternalSiteLauncher().launchBrowser(mActivity.getString(R.string.offers_list_terms_url), mActivity);
    }

    /** {@inheritDoc} */
    @Override
    public OffersListModel createModel() {
        OffersListModel model = new OffersListModel();
        model.setBaseData(LinkStorage.getInstance().getLoanData());

        return model;
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(OffersListView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);

        mView.setData(mModel);
        mView.setListener(this);

        mDialog.setListener(this);
        mNotificationDialog.setTitle(mActivity.getString(R.string.offers_list_dialog_disclaimer_title));

        reloadOffers();
    }

    @Override
    public void onBack() {
        mDelegate.onOffersListBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setAdapter(null);
        mView.setListener(null);
        mDialog.setListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void updateClickedHandler() {
        mDelegate.onUpdateUserProfile();
    }

    /** {@inheritDoc} */
    @Override
    public void applyClickHandler(OfferSummaryModel offer) {
        if (offer != null) {
            if (offer.requiresWebApplication()) {
                LoanApplicationDetailsResponseVo application = new LoanApplicationDetailsResponseVo();
                application.offer = new OfferVo();
                application.offer.application_method = LoanApplicationMethod.WEB;
                application.offer.application_url = offer.getOfferApplicationUrl();

                // Temporarily store fake application details.
                mLoanStorage.setCurrentLoanApplication(application);

                // Show dialog.
                mDialog.setTitle(mActivity.getString(R.string.offers_list_dialog_redirect_title));
                mDialog.setMessage(
                        mActivity.getString(R.string.offers_list_dialog_redirect_message, offer.getLenderName()));
                mDialog.show(mActivity.getFragmentManager(), DualOptionDialogFragment.DIALOG_TAG);
            } else {
                if (mView != null) {
                    mView.showLoading(true);
                }

                LedgeLinkUi.createLoanApplication(offer.getOfferId());
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void moreInfoClickHandler(OfferSummaryModel offer) {
        if (offer != null) {
            mNotificationDialog.setMessage(offer.getDisclaimer());
            mNotificationDialog.show(mActivity.getFragmentManager(), NotificationDialogFragment.DIALOG_TAG);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void offersClickHandler() { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void infoClickHandler() { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void bigButtonClickHandler(int action) {
        if (action == BigButtonModel.Action.RELOAD_LOAN_OFFERS) {
            reloadOffers();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void positiveButtonClickHandler(String dialogTag) {
        LoanApplicationDetailsResponseVo application = mLoanStorage.getCurrentLoanApplication();
        mLoanStorage.setCurrentLoanApplication(null);

        if (application != null && application.offer != null && !TextUtils.isEmpty(application.offer.application_url)) {
            new ExternalSiteLauncher().launchBrowser(application.offer.application_url, mActivity);
        } else {
            clearAdapter();
            mView.showError(true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void negativeButtonClickHandler(String dialogTag) {
        dismissHandler(dialogTag);
    }

    /** {@inheritDoc} */
    @Override
    public void dismissHandler(String dialogTag) { /* Do nothing. */ }

    /**
     * Adds a list of offers and displays them.
     * @param rawOffers List of offers.
     * @param offerRequestId Offer request ID.
     * @param complete Whether the list is complete.
     */
    public void addOffers(OfferVo[] rawOffers, int offerRequestId, boolean complete) {
        mLoanStorage.setOfferRequestId(offerRequestId);
        mLoanStorage.addOffers(mActivity.getResources(), rawOffers, complete, LedgeLinkUi.getImageLoader());

        PagedList<OfferSummaryModel> offers = mLoanStorage.getOffers();
        mAdapter.updateList(offers);

        if (mView != null) {
            mView.showLoading(false);
            mView.showError(false);
            mView.showEmptyCase(offers.isComplete() && (offers.getList() == null || offers.getList().size() <= 0));
        }
    }

    /**
     * Called when the initial offers list API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleOffers(InitialOffersResponseVo response) {
        if (response.offers != null) {
            addOffers(response.offers.data, response.offer_request_id, true);
        }
    }

    /**
     * Called when a loan application response has been received.
     * @param response API response.
     */
    @Subscribe
    public void showLoanApplicationScreen(LoanApplicationDetailsResponseVo response) {
        mView.showLoading(false);
        LoanStorage.getInstance().setCurrentLoanApplication(response);

        switch (response.status) {
            case LoanApplicationStatus.APPLICATION_REJECTED:
            case LoanApplicationStatus.PENDING_LENDER_ACTION:
            case LoanApplicationStatus.PENDING_BORROWER_ACTION:
            case LoanApplicationStatus.APPLICATION_RECEIVED:
                mDelegate.onApplicationReceived();
                break;
            case LoanApplicationStatus.LENDER_REJECTED:
            case LoanApplicationStatus.BORROWER_REJECTED:
            case LoanApplicationStatus.LOAN_APPROVED:
            default:
                Toast.makeText(mActivity, "Screen not yet implemented.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if (mView != null) {
            mView.showLoading(false);
        }

        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();

        if (LinkApiWrapper.INITIAL_OFFERS_PATH.equals(error.request_path) && mView != null) {
            mView.showError(true);
        } else if (LinkApiWrapper.CREATE_LOAN_APPLICATION_PATH.equals(error.request_path)) {
            mDelegate.onApplicationReceived();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        int id = item.getItemId();
        if (id == R.id.menu_update_profile) {
            updateClickedHandler();
        } else if (id == R.id.menu_refresh) {
            reloadOffers();
        }  else if (id == R.id.menu_info) {
            showInfo();
        } else {
            handled = false;
        }

        return handled;
    }
}
