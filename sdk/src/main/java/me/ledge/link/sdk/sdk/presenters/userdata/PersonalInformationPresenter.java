package me.ledge.link.sdk.sdk.presenters.userdata;

import android.support.v7.app.AppCompatActivity;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.models.userdata.PersonalInformationModel;
import me.ledge.link.sdk.sdk.presenters.Presenter;
import me.ledge.link.sdk.sdk.views.userdata.NextButtonListener;
import me.ledge.link.sdk.sdk.views.userdata.PersonalInformationView;

/**
 * Concrete {@link Presenter} for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationPresenter
        extends UserDataPresenter<PersonalInformationModel, PersonalInformationView>
        implements NextButtonListener {

    /**
     * Creates a new {@link PersonalInformationPresenter} instance.
     * @param activity Activity.
     */
    public PersonalInformationPresenter(AppCompatActivity activity) {
        super(activity);
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
            mView.setPhone(Long.toString(mModel.getPhone()));
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

        super.nextClickHandler();
    }
}
