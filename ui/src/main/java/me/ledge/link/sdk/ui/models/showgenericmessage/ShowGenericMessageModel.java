package me.ledge.link.sdk.ui.models.showgenericmessage;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;
import me.ledge.link.sdk.ui.models.userdata.AbstractUserDataModel;
import me.ledge.link.sdk.ui.models.userdata.UserDataModel;

/**
 * Concrete {@link Model} for the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessageModel
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
        // Title is overridden onAttachView
        return R.string.show_generic_message_title;
    }
}
