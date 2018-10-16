package com.shiftpayments.link.sdk.ui.presenters.verification;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.models.verification.PhoneVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataPresenter;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.utils.LoadingSpinnerManager;
import com.shiftpayments.link.sdk.ui.views.verification.VerificationView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Concrete {@link Presenter} for the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationPresenter
        extends UserDataPresenter<PhoneVerificationModel, VerificationView>
        implements VerificationView.ViewListener {

    private PhoneVerificationDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;
    /**
     * Creates a new {@link PhoneVerificationPresenter} instance.
     * @param activity Activity.
     */
    public PhoneVerificationPresenter(AppCompatActivity activity, PhoneVerificationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        ShiftPlatform.startVerification(mModel.getPhoneVerificationRequest());
    }

    /** {@inheritDoc} */
    @Override
    public PhoneVerificationModel createModel() {
        return new PhoneVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(VerificationView view) {
        super.attachView(view);
        mView.setListener(this);
        if(mModel.hasPhoneNumber()) {
            mView.setDataPoint(mModel.getFormattedPhoneNumber());
        }
        else {
            mView.setDescription(mActivity.getString(R.string.phone_verification_code_hint));
            mView.showDataPoint(false);
        }
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(true);
        mResponseHandler.subscribe(this);
    }

    @Override
    public void onBack() {
        mDelegate.phoneVerificationOnBackPressed();
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
        mModel.setVerificationCode(mView.getVerificationCode());

        if (mModel.hasVerificationCode()) {
            ShiftPlatform.completeVerification(mModel.getVerificationRequest());
        }
        else {
            displayWrongCodeMessage();
        }
    }

    /**
     * Called when the restart verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(VerificationResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            Log.d("ShiftVerification", "secret: " + response.secret);
            mModel.setVerification(response.verification_id, response.verification_type);
        }
    }

    /**
     * Called when the finish phone verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(FinishVerificationResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            mModel.setVerificationStatus(response.status);
            if(mModel.hasValidData()) {
                mDelegate.phoneVerificationSucceeded(response);
            }
            else {
                displayWrongCodeMessage();
            }
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        mLoadingSpinnerManager.showLoading(false);
        super.setApiError(error);
    }

    @Override
    public void resendClickHandler() {
        mLoadingSpinnerManager.showLoading(true);
        ShiftPlatform.restartVerification(mModel.getVerificationId());
        mView.displaySentMessage(mActivity.getString(R.string.phone_verification_resent));
    }

    private void displayWrongCodeMessage() {
        ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.verification_error), mActivity);
        mView.clearPinView();
    }
}
