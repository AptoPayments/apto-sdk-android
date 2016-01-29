package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.activities.userdata.AddressActivity;
import us.ledge.line.sdk.sdk.activities.userdata.IdentityVerificationActivity;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * Concrete {@link Model} for the income screen.
 * @author Wijnand
 */
public class IncomeModel implements UserDataModel {

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.income_label;
    }

    /** {@inheritDoc} */
    @Override
    public Class getPreviousActivity() {
        return AddressActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class getNextActivity() {
        return IdentityVerificationActivity.class;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return true;
    }
}
