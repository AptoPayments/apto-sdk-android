package com.shiftpayments.link.sdk.ui.models.showgenericmessage;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.models.userdata.AbstractUserDataModel;
import com.shiftpayments.link.sdk.ui.models.userdata.UserDataModel;

/**
 * Concrete {@link Model} for the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessageModel
        extends AbstractUserDataModel
        implements UserDataModel, ActivityModel, Model {

    /** {@inheritDoc} */
    @Override
    public boolean hasValidData() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int getActivityTitleResource() {
        // Title is overridden onAttachView
        return R.string.show_generic_message_title;
    }
}
