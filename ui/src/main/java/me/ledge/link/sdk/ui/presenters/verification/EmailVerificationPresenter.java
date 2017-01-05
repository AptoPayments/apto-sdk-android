package me.ledge.link.sdk.ui.presenters.verification;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.api.vos.VerificationVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.vos.responses.verifications.StartEmailVerificationResponseVo;
import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.verification.EmailVerificationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataPresenter;
import me.ledge.link.sdk.ui.storages.UserStorage;
import me.ledge.link.sdk.ui.views.verification.EmailVerificationView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

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
        LedgeLinkUi.startEmailVerification(mModel.getEmailVerificationRequest());
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 4, true, true);
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

        mActivity.setTitle(this.getEmail());

        mView.setListener(this);
        mView.displayEmailAddress(this.getEmail());
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
        LedgeLinkUi.getVerificationStatus(mModel.getVerificationId());
    }

    private String getEmail() {
        DataPointVo.Email email = (DataPointVo.Email) mModel.getBaseData().
                getUniqueDataPoint(DataPointVo.DataPointType.Email, new DataPointVo.Email());
        return email.email;
    }

    /**
     * Deals with the start email verification API response.
     * @param response API response.
     */
    public void setVerificationResponse(StartEmailVerificationResponseVo response) {
        if (response != null) {
            DataPointVo.Email email = mModel.getEmailFromBaseData();
            if(email.hasVerification()) {
                email.getVerification().setVerificationId(response.verification_id);
            }
            else{
                email.setVerification(new VerificationVo(response.verification_id));
            }
        }
    }

    /**
     * Deals with the verification status API response.
     * @param response API response.
     */
    public void setVerificationResponse(VerificationStatusResponseVo response) {
        if (response != null) {
            DataPointVo.Email email = mModel.getEmailFromBaseData();
            email.getVerification().setVerificationStatus(response.status);
            if(!email.getVerification().isVerified()) {
                displayWrongCodeMessage();
                return;
            }
            else {
                mDelegate.emailVerificationSucceeded(email, this);
            }
        }
    }

    /**
     * Deals with the create user API response.
     * @param response API response.
     */
    public void setCreateUserResponse(CreateUserResponseVo response) {
        if (response != null) {
            UserStorage.getInstance().setBearerToken(response.user_token);
            startGivenActivity(mModel.getLastActivity());
        }
    }

    @Override
    public void resendClickHandler() {
        LedgeLinkUi.startEmailVerification(mModel.getEmailVerificationRequest());
        displaySentMessage();
    }

    private void displaySentMessage() {
        String message = mActivity.getString(R.string.email_verification_resent);
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }

    private void displayWrongCodeMessage() {
        String message = mActivity.getString(R.string.email_verification_error);
        Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
    }
}
