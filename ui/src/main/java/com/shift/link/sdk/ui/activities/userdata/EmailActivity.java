package com.shift.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.userdata.EmailModel;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.presenters.userdata.EmailDelegate;
import com.shift.link.sdk.ui.presenters.userdata.EmailPresenter;
import com.shift.link.sdk.ui.views.userdata.EmailView;

/**
 * Created by pauteruel on 19/02/2018.
 */

public class EmailActivity
    extends UserDataActivity<EmailModel, EmailView, EmailPresenter> {

    /** {@inheritDoc} */
    @Override
    protected EmailView createView() {
        return (EmailView) View.inflate(this, R.layout.act_email, null);
    }

    /** {@inheritDoc} */
    @Override
    protected EmailPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof EmailDelegate) {
            return new EmailPresenter(this, (EmailDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement EmailDelegate!");
        }
    }

}
