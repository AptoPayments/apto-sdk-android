package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.PersonalInformationModel;
import me.ledge.link.sdk.ui.presenters.userdata.PersonalInformationPresenter;
import me.ledge.link.sdk.ui.presenters.userdata.UserDataCollectorModule;
import me.ledge.link.sdk.ui.views.userdata.PersonalInformationView;

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
    protected PersonalInformationPresenter createPresenter() {
        return new PersonalInformationPresenter(this, UserDataCollectorModule.getInstance(this));
    }
}
