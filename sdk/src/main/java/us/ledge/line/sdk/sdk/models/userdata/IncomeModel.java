package us.ledge.line.sdk.sdk.models.userdata;

import us.ledge.line.sdk.sdk.R;
import us.ledge.line.sdk.sdk.models.ActivityModel;
import us.ledge.line.sdk.sdk.models.Model;

/**
 * TODO: Class documentation.
 *
 * @author Wijnand
 */
public class IncomeModel implements ActivityModel, UserDataModel, Model {

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.income_label;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return false;
    }
}
