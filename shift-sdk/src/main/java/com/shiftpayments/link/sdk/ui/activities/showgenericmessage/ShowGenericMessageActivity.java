package com.shiftpayments.link.sdk.ui.activities.showgenericmessage;

import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.showgenericmessage.ShowGenericMessageModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessageDelegate;
import com.shiftpayments.link.sdk.ui.presenters.showgenericmessage.ShowGenericMessagePresenter;
import com.shiftpayments.link.sdk.ui.views.showgenericmessage.ShowGenericMessageView;

/**
 * Wires up the MVP pattern for the show generic message screen.
 * @author Adrian
 */
public class ShowGenericMessageActivity extends MvpActivity<ShowGenericMessageModel, ShowGenericMessageView, ShowGenericMessagePresenter> {

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
