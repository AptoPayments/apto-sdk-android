package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.PersonalInformationConfirmationModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.userdata.PersonalInformationConfirmationView;

/**
 * Concrete {@link Presenter} for the personal information confirmation screen.
 * @author Adrian
 */
public class PersonalInformationConfirmationPresenter
        extends UserDataPresenter<PersonalInformationConfirmationModel, PersonalInformationConfirmationView>
        implements PersonalInformationConfirmationView.ViewListener {

    private PersonalInformationConfirmationDelegate mDelegate;

    /**
     * Creates a new {@link PersonalInformationConfirmationPresenter} instance.
     * @param activity Activity.
     */
    public PersonalInformationConfirmationPresenter(AppCompatActivity activity, PersonalInformationConfirmationDelegate delegate) {
        super(activity);
        mDelegate = delegate;

    }

    /** {@inheritDoc} */
    @Override
    public PersonalInformationConfirmationModel createModel() {
        return new PersonalInformationConfirmationModel();
    }

    /** {@inheritDoc} */
    @Override
    public void attachView(PersonalInformationConfirmationView view) {
        super.attachView(view);
        mView.setListener(this);
        if(mModel.hasPersonalName()) {
            mView.setFirstName(mModel.getFirstName());
            mView.setLastName(mModel.getLastName());
        }
        if(mModel.hasEmail()) {
            mView.setEmail(mModel.getEmail());
        }
        if(mModel.hasAddress()) {
            mView.setAddress(mModel.getAddress());
        }
        if(mModel.hasPhoneNumber()) {
            mView.setPhone(mModel.getPhoneNumber());
        }
        mView.setNextButtonText(mActivity.getResources().getString(R.string.toolbar_confirm_button_label));
        mView.enableNextButton(true);
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
        mDelegate.personalInformationConfirmed();
    }
}
