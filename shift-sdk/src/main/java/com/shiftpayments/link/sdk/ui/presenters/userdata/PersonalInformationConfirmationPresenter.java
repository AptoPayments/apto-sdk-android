package com.shiftpayments.link.sdk.ui.presenters.userdata;

import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.ui.models.userdata.PersonalInformationConfirmationModel;
import com.shiftpayments.link.sdk.ui.presenters.Presenter;
import com.shiftpayments.link.sdk.ui.views.userdata.PersonalInformationConfirmationView;

/**
 * Concrete {@link Presenter} for the personal information screen.
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
        mView.setFirstName("Adrian");
        mView.setLastName("Tung");
        mView.setEmail("adrian@shiftpayments.com");
        mView.setAddress("41 Highbury Pl, Highbury East, London N5 1QL, UK");
        mView.setPhone("+44 77 1234 5678");
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
        saveData();
        mDelegate.personalInformationConfirmed();
    }
}
