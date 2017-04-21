package me.ledge.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;
import me.ledge.link.sdk.ui.ModuleManager;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.PersonalInformationModel;
import me.ledge.link.sdk.ui.presenters.Presenter;
import me.ledge.link.sdk.ui.views.userdata.PersonalInformationView;
import me.ledge.link.sdk.ui.widgets.steppers.StepperConfiguration;

/**
 * Concrete {@link Presenter} for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationPresenter
        extends UserDataPresenter<PersonalInformationModel, PersonalInformationView>
        implements PersonalInformationView.ViewListener {

    private PersonalInformationDelegate mDelegate;
    private boolean mIsNameRequired;
    private boolean mIsPhoneRequired;
    private boolean mIsEmailRequired;

    /**
     * Creates a new {@link PersonalInformationPresenter} instance.
     * @param activity Activity.
     */
    public PersonalInformationPresenter(AppCompatActivity activity, PersonalInformationDelegate delegate) {
        super(activity);
        mDelegate = delegate;
        UserDataCollectorModule module = (UserDataCollectorModule) ModuleManager.getInstance().getCurrentModule();
        mIsNameRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.PersonalName.ordinal()+1));
        mIsPhoneRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.PhoneNumber.ordinal()+1));
        mIsEmailRequired = module.mRequiredDataPointList.contains(new RequiredDataPointVo(DataPointVo.DataPointType.Email.ordinal()+1));
    }

    /** {@inheritDoc} */
    @Override
    protected StepperConfiguration getStepperConfig() {
        return new StepperConfiguration(TOTAL_STEPS, 1, true, true);
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

        mView.showName(mIsNameRequired);
        if (mIsNameRequired && mModel.hasFirstName()) {
            mView.setFirstName(mModel.getFirstName());
        }
        if (mIsNameRequired && mModel.hasLastName()) {
            mView.setLastName(mModel.getLastName());
        }
        mView.showEmail(mIsEmailRequired);
        if (mIsEmailRequired && mModel.hasEmail()) {
            mView.setEmail(mModel.getEmail());
        }
        mView.showPhone(mIsPhoneRequired);
        if (mIsPhoneRequired && mModel.hasPhone()) {
            mView.setPhone(Long.toString(mModel.getPhone().getNationalNumber()));
        }

        mView.setListener(this);
    }

    @Override
    public void onBack() {
        mDelegate.personalInformationOnBackPressed();
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
        if(mIsNameRequired) {
            mModel.setFirstName(mView.getFirstName());
            mModel.setLastName(mView.getLastName());
            mView.updateFirstNameError(!mModel.hasFirstName(), R.string.personal_info_first_name_error);
            mView.updateLastNameError(!mModel.hasLastName(), R.string.personal_info_last_name_error);
        }

        if(mIsEmailRequired) {
            mModel.setEmail(mView.getEmail());
            mView.updateEmailError(!mModel.hasEmail(), R.string.personal_info_email_error);
        }

        if(mIsPhoneRequired) {
            mModel.setPhone(mView.getPhone());
            mView.updatePhoneError(!mModel.hasPhone(), R.string.personal_info_phone_error);
        }

        if(mModel.hasAllRequiredData(mIsNameRequired, mIsEmailRequired, mIsPhoneRequired)) {
            saveData();
            mDelegate.personalInformationStored();
        }
    }
}
