package me.ledge.link.sdk.ui.activities.userdata;

import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.userdata.EmailModel;
import me.ledge.link.sdk.ui.presenters.userdata.BaseDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.EmailDelegate;
import me.ledge.link.sdk.ui.presenters.userdata.EmailPresenter;
import me.ledge.link.sdk.ui.views.userdata.EmailView;

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
