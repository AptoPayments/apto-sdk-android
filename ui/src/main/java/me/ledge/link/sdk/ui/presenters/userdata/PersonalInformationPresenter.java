package me.ledge.link.sdk.ui.presenters.userdata;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import me.ledge.link.api.vos.DataPointVo;
import me.ledge.link.sdk.ui.LedgeLinkUi;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.PersonalInformationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.EmailVerificationPresenter;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationDelegate;
import me.ledge.link.sdk.ui.presenters.verification.PhoneVerificationPresenter;
import me.ledge.link.sdk.ui.views.userdata.PersonalInformationView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationPresenter
        extends UserDataPresenter<PersonalInformationModel, PersonalInformationView>
        implements PersonalInformationView.ViewListener, PhoneVerificationDelegate,
        EmailVerificationDelegate {

    // TODO: refactor when flow manager is implemented
    private EmailVerificationPresenter mPresenter;

    /**
     * Creates a new {@link PersonalInformationPresenter} instance.
     * @param activity Activity.
     */
    public PersonalInformationPresenter(AppCompatActivity activity) {
        super(activity);
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 2, true, true);
    }

    /** {@inheritDoc} */
    @Override
    public PersonalInformationModel createModel() {
        return new PersonalInformationModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PersonalInformationView view) {
        super.attachView(view);

        if (mModel.hasFirstName()) {
            mView.setFirstName(mModel.getFirstName());
        }
        if (mModel.hasLastName()) {
            mView.setLastName(mModel.getLastName());
        }
        if (mModel.hasEmail()) {
            mView.setEmail(mModel.getEmail());
        }
        if (mModel.hasPhone()) {
            mView.setPhone(Long.toString(mModel.getPhone().getNationalNumber()));
        }

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
        mModel.setFirstName(mView.getFirstName());
        mModel.setLastName(mView.getLastName());
        mModel.setEmail(mView.getEmail());
        mModel.setPhone(mView.getPhone());

        // Validate data.
        mView.updateFirstNameError(!mModel.hasFirstName(), R.string.personal_info_first_name_error);
        mView.updateLastNameError(!mModel.hasLastName(), R.string.personal_info_last_name_error);
        mView.updateEmailError(!mModel.hasEmail(), R.string.personal_info_email_error);
        mView.updatePhoneError(!mModel.hasPhone(), R.string.personal_info_phone_error);

        // TODO: start phone verification only if enabled
        super.nextClickHandler();
    }

    @Override
    public void phoneVerificationSucceeded(DataPointVo.PhoneNumber phone,
                                           PhoneVerificationPresenter phoneVerificationPresenter) {
        phoneVerificationPresenter.startNextActivity();
    }

    @Override
    public void newPhoneVerificationSucceeded(DataPointVo.PhoneNumber phone,
                                              PhoneVerificationPresenter phoneVerificationPresenter,
                                              Activity current) {
        // Skip email verification
        phoneVerificationPresenter.startGivenActivity(mModel.getNextFollowingActivity(current));
    }

    @Override
    public void emailVerificationSucceeded(DataPointVo.Email email, EmailVerificationPresenter emailVerificationPresenter) {
        LedgeLinkUi.loginUser(mModel.getLoginData());
    }
}
