package me.ledge.link.sdk.ui.models.link;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.userdata.AbstractUserDataModel;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;

/**
 * Concrete {@link Model} for the terms and conditions screen.
 * @author Wijnand
 */
public class TermsModel
        extends AbstractUserDataModel
        implements UserDataModel, ActivityModel, Model {

    /** {@inheritDoc} */
    @Override
    public boolean hasAllData() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        return R.string.terms_label;
    }
}
