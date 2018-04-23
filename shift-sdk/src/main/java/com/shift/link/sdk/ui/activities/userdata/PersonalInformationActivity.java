package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.PersonalInformationModel;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PersonalInformationDelegate;
import com.shift.link.sdk.ui.presenters.userdata.PersonalInformationPresenter;
import com.shift.link.sdk.ui.views.userdata.PersonalInformationView;

/**
 * Wires up the MVP pattern for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationActivity
        extends UserDataActivity<PersonalInformationModel, PersonalInformationView, PersonalInformationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationView createView() {
        return (PersonalInformationView) View.inflate(this, R.layout.act_personal_information, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PersonalInformationDelegate) {
            return new PersonalInformationPresenter(this, (PersonalInformationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement PersonalInformationDelegate!");
        }
    }
}
