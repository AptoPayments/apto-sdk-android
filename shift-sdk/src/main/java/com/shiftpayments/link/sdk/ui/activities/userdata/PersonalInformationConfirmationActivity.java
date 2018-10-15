package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.userdata.PersonalInformationConfirmationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PersonalInformationConfirmationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.PersonalInformationConfirmationPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.PersonalInformationConfirmationView;


/**
 * Wires up the MVP pattern for the personal information confirmation screen.
 * @author Adrian
 */
public class PersonalInformationConfirmationActivity
        extends MvpActivity<PersonalInformationConfirmationModel, PersonalInformationConfirmationView, PersonalInformationConfirmationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationConfirmationView createView() {
        return (PersonalInformationConfirmationView) View.inflate(this, R.layout.act_personal_information_confirmation, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationConfirmationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PersonalInformationConfirmationDelegate) {
            return new PersonalInformationConfirmationPresenter(this, (PersonalInformationConfirmationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement PersonalInformationConfirmationDelegate!");
        }
    }
}
