package com.shift.link.sdk.ui.activities.showgenericmessage;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.userdata.UserDataActivity;
import com.shift.link.sdk.ui.models.showgenericmessage.ShowGenericMessageModel;
import com.shift.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageDelegate;
import com.shift.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessagePresenter;
import com.shift.link.sdk.ui.presenters.BaseDelegate;
import com.shift.link.sdk.ui.views.showgenericmessage.ShowGenericMessageView;

/**
 * Wires up the MVP pattern for the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessageActivity extends UserDataActivity<ShowGenericMessageModel, ShowGenericMessageView, ShowGenericMessagePresenter> {

    /** {@inheritDoc} */
    @Override
    protected ShowGenericMessageView createView() {
        return (ShowGenericMessageView) View.inflate(this, R.layout.act_show_generic_message, null);
    }

    /** {@inheritDoc} */
    @Override
    protected ShowGenericMessagePresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof ShowGenericMessageDelegate) {
            return new ShowGenericMessagePresenter(this, (ShowGenericMessageDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ShowGenericMessageDelegate!");
        }
    }
}
