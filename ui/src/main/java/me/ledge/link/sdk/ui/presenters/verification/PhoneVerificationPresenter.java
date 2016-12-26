package me.ledge.link.sdk.ui.presenters.verification;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.ledge.link.api.vos.CredentialVo;
import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.VerificationVo;
import me.ledge.link.api.vos.responses.verifications.FinishVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartVerificationResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.verification.PhoneVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.utils.PhoneHelperUtil;
import me.ledge.link.sdk.ui.views.verification.PhoneVerificationView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the phone verification screen.
 * @author Adrian
 */
public class PhoneVerificationPresenter
        extends UserDataPresenter<PhoneVerificationModel, PhoneVerificationView>
        implements PhoneVerificationView.ViewListener {

    /**
     * Creates a new {@link PhoneVerificationPresenter} instance.
     * @param activity Activity.
     */
    public PhoneVerificationPresenter(AppCompatActivity activity) {
        super(activity);
        LedgeLinkUi.startPhoneVerification(mModel.getPhoneVerificationRequest());
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 3, true, true);
    }

    /** {@inheritDoc} */
    @Override
    public PhoneVerificationModel createModel() {
        return new PhoneVerificationModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PhoneVerificationView view) {
        super.attachView(view);

        mActivity.setTitle(this.getTitle());

        mView.setListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void detachView() {
        mView.setListener(null);
        super.detachView();
    }

    /** {@inheritDoc} */
    @Override
    public void nextClickHandler() {
        // Store data.
        mModel.setVerificationCode(mView.getVerificationCode());

        if (mModel.hasAllData()) {
            LedgeLinkUi.completePhoneVerification(mModel.getVerificationRequest());
        }
    }

    private String getTitle() {
        DataPointVo.PhoneNumber phoneNumber = (DataPointVo.PhoneNumber) mModel.getBaseData().
                getUniqueDataPoint(DataPointVo.DataPointType.PhoneNumber, new DataPointVo.PhoneNumber());
        return PhoneHelperUtil.formatPhone(phoneNumber.phoneNumber);
    }

    /**
     * Deals with the start phone verification API response.
     * @param response API response.
     */
    public void setVerificationResponse(StartVerificationResponseVo response) {
        if (response != null) {
            DataPointVo.PhoneNumber phone = mModel.getPhoneFromBaseData();
            if(phone.hasVerification()) {
                phone.getVerification().setVerificationId(response.verification_id);
            }
            else{
                phone.setVerification(new VerificationVo(response.verification_id));
            }
        }
    }

    /**
     * Deals with the finish phone verification API response.
     * @param response API response.
     */
    public void setVerificationResponse(FinishVerificationResponseVo response) {
        if (response != null) {
            DataPointVo.PhoneNumber phone = mModel.getPhoneFromBaseData();
            phone.getVerification().setVerificationStatus(response.status);
            if(!phone.getVerification().isVerified()) {
                displayWrongCodeMessage();
                return;
            }
            else if(response.alternate_credentials.total_count == 0) {
                // New phone, continuing with user creation (skip email activity)
            }
            else {
                // Start email verification
                CredentialVo credentialVo = response.alternate_credentials.data.get(0);
                phone.getVerification().setAlternateCredentials(response.alternate_credentials.data);
                // TODO: email verification API call
            }
        }

        // TODO: decide which screen is shown depending on verification response
        super.nextClickHandler();
    }

    @Override
    public void resendClickHandler() {
        LedgeLinkUi.startPhoneVerification(mModel.getPhoneVerificationRequest());
        displaySentMessage();
    }

    private void displaySentMessage() {
        String message = mActivity.getString(R.string.phone_verification_resent);
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    private void displayWrongCodeMessage() {
        String message = mActivity.getString(R.string.phone_verification_error);
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        mView.clearPinView();
    }
}
