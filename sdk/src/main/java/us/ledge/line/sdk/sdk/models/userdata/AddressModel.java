package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.ActivityModel;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Concrete {@link Model} for the address screen.
 * @author Wijnand
 */
public class AddressModel implements ActivityModel, UserDataModel, Model {

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.address_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return false;
    }
}
