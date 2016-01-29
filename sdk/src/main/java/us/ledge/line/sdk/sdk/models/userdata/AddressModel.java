package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.userdata.IncomeActivity;
import us.ledge.line.sdk.sdk.activities.userdata.PersonalInformationActivity;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class AddressModel implements UserDataModel {

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.address_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity() {
        return PersonalInformationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return IncomeActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return true;
    }
}
