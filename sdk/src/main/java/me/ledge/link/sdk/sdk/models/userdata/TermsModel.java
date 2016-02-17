package me.ledge.link.sdk.sdk.models.userdata;

import me.ledge.link.sdk.sdk.R;
import me.ledge.link.sdk.sdk.models.ActivityModel;
import me.ledge.link.sdk.sdk.models.Model;

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
