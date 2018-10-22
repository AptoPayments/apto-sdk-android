package com.shiftpayments.link.sdk.ui.presenters.card;

import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.activities.card.PhysicalCardActivationActivity;
import com.shiftpayments.link.sdk.ui.models.card.PhysicalCardActivationModel;
import com.shiftpayments.link.sdk.ui.presenters.ActivityPresenter;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.storages.CardStorage;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.verification.VerificationView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Concrete {@link Presenter} for the physical card activation screen.
 * @author Adrian
 */
public class PhysicalCardActivationPresenter
        extends ActivityPresenter<PhysicalCardActivationModel, VerificationView>
        implements VerificationView.ViewListener {

    private PhysicalCardActivationDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link PhysicalCardActivationPresenter} instance.
     * @param activity Activity.
     */
    public PhysicalCardActivationPresenter(PhysicalCardActivationActivity activity, PhysicalCardActivationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
    }

    /** {@inheritDoc} */
    @Override
    public PhysicalCardActivationModel createModel() {
        return new PhysicalCardActivationModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(VerificationView view) {
        super.attachView(view);
        mView.setListener(this);
        mView.setDescription(mActivity.getString(R.string.physical_card_activation_hint));
        mView.showDataPoint(false);
        mView.showResendButton(false);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
        mResponseHandler.subscribe(this);
    }

    @Override
    public void onBack() {
        mDelegate.activatePhysicalCardOnBackPressed();
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mResponseHandler.unsubscribe(this);
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        mLoadingSpinnerManager.showLoading(true);
        // Store data.
        mModel.setActivationCode(mView.getVerificationCode());

        if (mModel.hasActivationCode()) {
            ShiftPlatform.activatePhysicalCard(CardStorage.getInstance().getCard().mAccountId, mModel.getActivationCode());
        }
    }

    /**
     * Called when the activate physical card API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(ApiEmptyResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            mDelegate.physicalCardActivated();
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        //TODO testing
        mDelegate.physicalCardActivated();
        /*mLoadingSpinnerManager.showLoading(false);
        mView.clearPinView();
        ApiErrorUtil.showErrorMessage(error, mActivity);*/
    }

    @Override
    public void resendClickHandler() {
    }

    @Override
    public void stepperNextClickHandler() {
    }
}
