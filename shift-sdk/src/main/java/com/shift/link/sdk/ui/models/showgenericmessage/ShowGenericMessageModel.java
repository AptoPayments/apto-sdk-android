package com.shift.link.sdk.ui.models.showgenericmessage;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;
import com.shift.link.sdk.ui.models.userdata.AbstractUserDataModel;
import com.shift.link.sdk.ui.models.userdata.UserDataModel;

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
