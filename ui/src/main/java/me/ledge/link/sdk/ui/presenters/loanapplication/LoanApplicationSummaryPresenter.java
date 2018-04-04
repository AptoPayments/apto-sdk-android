package me.ledge.link.sdk.ui.presenters.loanapplication;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.github.ksoichiro.android.observablescrollview.ScrollState;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.LinkedList;

import java8.util.concurrent.CompletableFuture;
import me.ledge.link.sdk.api.vos.responses.ApiErrorVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanProductListVo;
import me.ledge.link.sdk.api.vos.responses.config.LoanProductVo;
import me.ledge.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.api.vos.responses.offers.OfferVo;
import me.ledge.link.sdk.sdk.storages.ConfigStorage;
import me.ledge.link.sdk.ui.ShiftUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.loanapplication.LoanApplicationSummaryModel;
import me.ledge.link.sdk.ui.presenters.ActivityPresenter;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.storages.LoanStorage;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.utils.LoadingSpinnerManager;
import me.ledge.link.sdk.ui.views.loanapplication.LoanApplicationSummaryView;
import me.ledge.link.sdk.ui.vos.ApplicationVo;

/**
 * Concrete {@link Presenter} for the loan application summary.
 * @author Adrian
 */
public class LoanApplicationSummaryPresenter
        extends ActivityPresenter<LoanApplicationSummaryModel, LoanApplicationSummaryView>
        implements Presenter<LoanApplicationSummaryModel, LoanApplicationSummaryView>, LoanApplicationSummaryView.ViewListener {

    private LoanApplicationSummaryDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link LoanApplicationSummaryPresenter} instance.
     * @param activity Activity.
     */
    public LoanApplicationSummaryPresenter(AppCompatActivity activity, LoanApplicationSummaryDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        init();
    }

    @Override
    public void init() {
        super.init();
        CompletableFuture
                .supplyAsync(()-> ConfigStorage.getInstance().getLoanProducts())
                .thenAccept(this::applicationDisclaimerRetrieved);
        setRequiredData(UserStorage.getInstance().getRequiredData());
    }

    /** {@inheritDoc} */
    @Override
    public LoanApplicationSummaryModel createModel() {
        return new LoanApplicationSummaryModel(LoanStorage.getInstance().getSelectedOffer(), ShiftUi.getImageLoader());
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(LoanApplicationSummaryView view) {
        super.attachView(view);
        mResponseHandler.subscribe(this);

        mView.setViewListener(this);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
        mView.updateBottomButton(false);
        mView.setData(mModel);
    }

    private void setRequiredData(RequiredDataPointsListResponseVo requiredDataPointsList) {
        if(requiredDataPointsList != null) {
            LinkedList<RequiredDataPointVo> requiredDataPointsLinkedList = new LinkedList<>(Arrays.asList(requiredDataPointsList.data));
            mModel.setRequiredData(requiredDataPointsLinkedList);
        }
    }

    private void applicationDisclaimerRetrieved(LoanProductListVo loanProductListVo) {
        if (loanProductListVo == null) {
            return;
        }

        String lineBreak = "<br />";
        String partnerDivider = "<br /><br />";
        StringBuilder applicationDisclaimer = new StringBuilder();

        for(LoanProductVo loanProduct : loanProductListVo.data) {
            if (!TextUtils.isEmpty(loanProduct.applicationDisclaimer.value)) {
                applicationDisclaimer.append(loanProduct.applicationDisclaimer.value.replaceAll("\\r?\\n", lineBreak));
            }
            applicationDisclaimer.append(partnerDivider);
        }

        mActivity.runOnUiThread(() -> {
            mView.setDisclaimer(applicationDisclaimer.substring(0, applicationDisclaimer.length() - partnerDivider.length()));
        });
    }

    @Override
    public void onBack() {
        mDelegate.loanApplicationSummaryShowPrevious(mModel);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setViewListener(null);
        mResponseHandler.unsubscribe(this);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (mView == null) {
            return;
        }

        boolean fullyScrolled = false;
        if (scrollY >= mView.getMaxScroll()) {
            fullyScrolled = true;
        }

        mView.updateBottomButton(fullyScrolled);
    }

    /** {@inheritDoc} */
    @Override
    public void onDownMotionEvent() { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) { /* Do nothing. */ }

    /** {@inheritDoc} */
    @Override
    public void confirmClickHandler() {
        if (mView.getCurrentScroll() >= mView.getMaxScroll()) {
            if (mView != null) {
                mLoadingSpinnerManager.showLoading(true);
            }
            OfferVo selectedOffer = LoanStorage.getInstance().getSelectedOffer();
            ShiftUi.createLoanApplication(selectedOffer.id);

        } else {
            mView.scrollToBottom();
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
        String message = mActivity.getString(R.string.id_verification_toast_api_error, error.toString());
        mView.displayErrorMessage(message);
    }
}
