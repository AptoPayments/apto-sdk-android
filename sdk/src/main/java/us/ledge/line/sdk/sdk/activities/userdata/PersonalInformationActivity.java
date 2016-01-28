package us.ledge.line.sdk.sdk.activities.userdata;

import android.view.View;
import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.MvpActivity;
import us.ledge.line.sdk.sdk.models.userdata.PersonalInformationModel;
import us.ledge.line.sdk.sdk.presenters.userdata.PersonalInformationPresenter;
import us.ledge.line.sdk.sdk.views.userdata.PersonalInformationView;

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
