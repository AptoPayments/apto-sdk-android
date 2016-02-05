package me.ledge.link.sdk.sdk.activities.userdata;

import android.view.View;
import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.activities.MvpActivity;
import me.ledge.link.sdk.sdk.models.userdata.PersonalInformationModel;
import me.ledge.link.sdk.sdk.presenters.userdata.PersonalInformationPresenter;
import me.ledge.link.sdk.sdk.views.userdata.PersonalInformationView;

/**
 * Wires up the MVP pattern for the personal information screen.
 * @author Wijnand
 */
public class PersonalInformationActivity
        extends MvpActivity<PersonalInformationModel, PersonalInformationView, PersonalInformationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationView createView() {
        return (PersonalInformationView) View.inflate(this, R.layout.act_personal_information, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PersonalInformationPresenter createPresenter() {
        return new PersonalInformationPresenter(this);
    }
}
