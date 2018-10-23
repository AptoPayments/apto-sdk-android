package com.shiftpayments.link.sdk.ui.presenters.verification;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.models.verification.EmailVerificationModel;
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
public class EmailVerificationPresenter
        extends UserDataPresenter<EmailVerificationModel, VerificationView>
        implements VerificationView.ViewListener {

    private EmailVerificationDelegate mDelegate;
    private LoadingSpinnerManager mLoadingSpinnerManager;

    /**
     * Creates a new {@link EmailVerificationPresenter} instance.
     * @param activity Activity.
     */
    public EmailVerificationPresenter(AppCompatActivity activity, EmailVerificationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        if(mDelegate.isStartVerificationRequired()){
            ShiftPlatform.startVerification(mModel.getEmailVerificationRequest());
        }
    }

    /** {@inheritDoc} */
    @Override
    public EmailVerificationModel createModel() {
        return new EmailVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(VerificationView view) {
        super.attachView(view);
        if(mModel.hasEmail()) {
            mView.setDataPoint(mModel.getEmail());
        }
        else {
            mView.setDescription(mActivity.getString(R.string.email_verification_code_hint));
            mView.showDataPoint(false);
        }
        mView.setListener(this);
        mLoadingSpinnerManager = new LoadingSpinnerManager(mView);
        mLoadingSpinnerManager.showLoading(false);
        mResponseHandler.subscribe(this);
    }

    @Override
    public void onBack() {
        mDelegate.emailVerificationOnBackPressed();
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
    }

    /**
     * Called when the finish phone verification API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(FinishVerificationResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            Log.d("ShiftVerification", "FinishVerificationResponseVo: " + response.secret);
            setVerificationResponse(response);
            mModel.setVerificationStatus(response.status);
            if(mModel.hasValidData()) {
                mDelegate.emailVerificationSucceeded(response);
            }
            else {
                ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.verification_error), mActivity);
                mView.clearPinView();
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

    /**
     * Deals with the restart verification API response.
     * @param response API response.
     */
    @Subscribe
    public void setVerificationResponse(VerificationResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            Log.d("ShiftVerification", "VerificationResponseVo: " + response.secret);
            Email email = mModel.getEmailFromBaseData();
            if(email.hasVerification()) {
                email.getVerification().setVerificationId(response.verification_id);
                email.getVerification().setVerificationType(response.verification_type);
            }
            else{
                email.setVerification(new VerificationVo(response.verification_id, response.verification_type));
            }
        }
    }

    /**
     * Deals with the verification status API response.
     * @param response API response.
     */
    public void setVerificationResponse(VerificationStatusResponseVo response) {
        mLoadingSpinnerManager.showLoading(false);
        if (response != null) {
            Log.d("ShiftVerification", "VerificationStatusResponseVo: " + response.secret);
            Email email = mModel.getEmailFromBaseData();
            email.getVerification().setVerificationStatus(response.status);
            if(!email.getVerification().isVerified()) {
                ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.verification_error), mActivity);
                mView.clearPinView();
            }
            else {
                mResponseHandler.unsubscribe(this);
                mDelegate.emailVerificationSucceeded(response);
            }
        }
    }

    @Override
    public void resendClickHandler() {
        mLoadingSpinnerManager.showLoading(true);
        ShiftPlatform.restartVerification(mModel.getVerificationId());
        mView.displaySentMessage(mActivity.getString(R.string.email_verification_resent));
    }
}
