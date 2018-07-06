package com.shiftpayments.link.sdk.ui.presenters.offers;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationMethod;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.OfferVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.adapters.offers.OffersListRecyclerAdapter;
import com.shiftpayments.link.sdk.ui.models.loanapplication.BigButtonModel;
import com.shiftpayments.link.sdk.ui.models.offers.OfferSummaryModel;
import com.shiftpayments.link.sdk.ui.models.offers.OffersListModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.LinkStorage;
import com.shiftpayments.link.sdk.ui.storages.LoanStorage;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.offers.OfferBaseSummaryView;
import com.shiftpayments.link.sdk.ui.views.offers.OfferCarouselSummaryView;
import com.shiftpayments.link.sdk.ui.views.offers.OffersBaseView;
import com.shiftpayments.link.sdk.ui.views.offers.OffersCarouselView;
import com.shiftpayments.link.sdk.ui.views.offers.OffersListView;
import com.shiftpayments.link.sdk.ui.vos.ApplicationVo;
import com.synnapps.carouselview.ViewListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import me.ledge.common.fragments.dialogs.DualOptionDialogFragment;
import me.ledge.common.fragments.dialogs.NotificationDialogFragment;
import me.ledge.common.utils.PagedList;
import me.ledge.common.utils.web.ExternalSiteLauncher;

/**
 * Concrete {@link Presenter} for the offers list screen.
 * TODO: Some loading indicator when offers are being loaded.
 * TODO: Not reload offers when they are already present.
 * @author Wijnand
 */
public class OffersListPresenter
        extends ActivityPresenter<OffersListModel, OffersBaseView>
        implements Presenter<OffersListModel, OffersBaseView>, OffersListView.ViewListener,
        OffersCarouselView.ViewListener, OfferBaseSummaryView.ViewListener,
        DualOptionDialogFragment.DialogListener {

    private LoanStorage mLoanStorage;
    private OffersListRecyclerAdapter mAdapter;
    private DualOptionDialogFragment mDialog;
    private NotificationDialogFragment mNotificationDialog;
    private LoadingSpinnerManager mLoadingSpinnerManager;

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
            mLoadingSpinnerManager.showLoading(true);
        }

        clearAdapter();
        mLoanStorage.clearOffers();

        // Fetch offers.
        InitialOffersRequestVo requestData = mModel.getInitialOffersRequest();
        requestData.rows = 25;
        ShiftPlatform.getInitialOffers(requestData);
    }

    /**
     * Clears the {@link OffersListRecyclerAdapter}.
     */
    private void clearAdapter() {
        if (mView instanceof OffersListView) {
            if (mAdapter != null) {
                // Clear current adapter.
                mAdapter.setViewListener(null);
            }

            // Create new one.
            mAdapter = new OffersListRecyclerAdapter();
            mAdapter.setViewListener(this);

            if (mView != null) {
                ((OffersListView) mView).setAdapter(mAdapter);
            }
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
    public void attachView(OffersBaseView view) {
        super.attachView(view);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
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
        if (mView instanceof OffersListView) {
            ((OffersListView) mView).setAdapter(null);
        }
        mView.setListener(null);
        mDialog.setListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void updateClickedHandler() {
        mDelegate.onUpdateLoan();
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
            } else if(offer.requiresConfirmation()) {
                mDelegate.onConfirmationRequired(offer);
            }
            else {
                if (mView != null) {
                    mLoadingSpinnerManager.showLoading(true);
                }

                ShiftPlatform.createLoanApplication(offer.getOfferId());
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
    public void addOffers(OfferVo[] rawOffers, String offerRequestId, boolean complete) {
        mLoanStorage.setOfferRequestId(offerRequestId);
        mLoanStorage.addOffers(mActivity.getResources(), rawOffers, complete, ShiftPlatform.getImageLoader());

        PagedList<OfferSummaryModel> offers = mLoanStorage.getOffers();

        if(mView instanceof OffersCarouselView) {
            ViewListener viewListener = new ViewListener() {
                List<OfferSummaryModel> offersList = OffersListPresenter.this.mLoanStorage.getOffers().getList();
                @Override
                public View setViewForPosition(int position) {

                    OfferCarouselSummaryView offerSummaryView = (OfferCarouselSummaryView) mActivity.getLayoutInflater().inflate(R.layout.sv_loan_offer, null);
                    offerSummaryView.setData(offersList.get(position));
                    offerSummaryView.setListener(OffersListPresenter.this);

                    return offerSummaryView;
                }
            };
            ((OffersCarouselView) mView).setCarouselViewListener(viewListener);
            ((OffersCarouselView) mView).displayOffers(offers.getList());
        }
        else {
            mAdapter.updateList(offers);
        }

        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
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
        mLoadingSpinnerManager.showLoading(false);
        LoanStorage.getInstance().setCurrentLoanApplication(response);
        ApplicationVo application = new ApplicationVo(response.id, response.next_action);
        mDelegate.onApplicationReceived(application);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        if (mView != null) {
            mLoadingSpinnerManager.showLoading(false);
        }

        String message = mActivity.getString(R.string.toast_api_error, error.toString());
        ApiErrorUtil.showErrorMessage(error, mActivity);

        if (ShiftApiWrapper.INITIAL_OFFERS_PATH.equals(error.request_path) && mView != null) {
            mView.showError(true);
        } else if (ShiftApiWrapper.CREATE_LOAN_APPLICATION_PATH.equals(error.request_path)) {
            mDelegate.onApplicationReceived(null);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        int id = item.getItemId();
        if (id == R.id.menu_update_profile) {
            mDelegate.onUpdateUserProfile();
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
