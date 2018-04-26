package com.shiftpayments.link.sdk.ui.activities.userdata;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.userdata.EmailModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.EmailDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.EmailPresenter;
import com.shiftpayments.link.sdk.ui.views.userdata.EmailView;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.EmailDelegate;
import com.shiftpayments.link.sdk.ui.presenters.userdata.EmailPresenter;

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
