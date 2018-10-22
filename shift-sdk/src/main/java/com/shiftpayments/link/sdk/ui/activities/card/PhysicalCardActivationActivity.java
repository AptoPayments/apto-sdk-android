package com.shiftpayments.link.sdk.ui.activities.card;

import android.view.Menu;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.MvpActivity;
import com.shiftpayments.link.sdk.ui.models.card.PhysicalCardActivationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.PhysicalCardActivationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.PhysicalCardActivationPresenter;
import com.shiftpayments.link.sdk.ui.views.verification.VerificationView;


public class PhysicalCardActivationActivity extends MvpActivity<PhysicalCardActivationModel,
        VerificationView, PhysicalCardActivationPresenter> {

    /** {@inheritDoc} */
    @Override
    protected VerificationView createView() {
        return (VerificationView) View.inflate(this, R.layout.act_datapoint_verification, null);
    }

    /** {@inheritDoc} */
    @Override
    protected PhysicalCardActivationPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof PhysicalCardActivationDelegate) {
            return new PhysicalCardActivationPresenter(this, (PhysicalCardActivationDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ManageCardDelegate!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do not show next button for this screen
        return true;
    }
}
