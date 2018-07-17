package com.shiftpayments.link.sdk.ui.presenters.verification;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationStatusResponseVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.models.verification.EmailVerificationModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.presenters.userdata.UserDataPresenter;
import com.shiftpayments.link.sdk.ui.utils.ApiErrorUtil;
import com.shiftpayments.link.sdk.ui.views.verification.EmailVerificationView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Concrete {@link Presenter} for the phone verification screen.
 * @author Adrian
 */
public class EmailVerificationPresenter
        extends UserDataPresenter<EmailVerificationModel, EmailVerificationView>
        implements EmailVerificationView.ViewListener {

    private EmailVerificationDelegate mDelegate;

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
    public void attachView(EmailVerificationView view) {
        super.attachView(view);
        String description = this.getEmail()==null ? "your email." : this.getEmail();
        mView.setDescription(mActivity.getResources().getString(R.string.email_verification_info, description));
        mView.setListener(this);
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
        ShiftPlatform.getVerificationStatus(mModel.getVerificationId());
    }

    /**
     * Called when the get verification status API response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(VerificationStatusResponseVo response) {
        setVerificationResponse(response);
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        super.setApiError(error);
    }

    private String getEmail() {
        Email email = (Email) mModel.getBaseData().
                getUniqueDataPoint(DataPointVo.DataPointType.Email, new Email());
        return email.email;
    }

    /**
     * Deals with the restart verification API response.
     * @param response API response.
     */
    @Subscribe
    public void setVerificationResponse(VerificationResponseVo response) {
        if (response != null) {
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
        if (response != null) {
            Email email = mModel.getEmailFromBaseData();
            email.getVerification().setVerificationStatus(response.status);
            if(!email.getVerification().isVerified()) {
                ApiErrorUtil.showErrorMessage(mActivity.getString(R.string.email_verification_error), mActivity);
            }
            else {
                mResponseHandler.unsubscribe(this);
                mDelegate.emailVerificationSucceeded(response);
            }
        }
    }

    @Override
    public void resendClickHandler() {
        ShiftPlatform.restartVerification(mModel.getVerificationId());
        mView.displaySentMessage(mActivity.getString(R.string.email_verification_resent));
    }
}
