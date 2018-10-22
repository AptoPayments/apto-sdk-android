package com.shiftpayments.link.sdk.ui.activities.card;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.models.card.PhysicalCardActivationModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.PhysicalCardActivationDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.PhysicalCardActivationPresenter;
import com.shiftpayments.link.sdk.ui.storages.UIStorage;
import com.shiftpayments.link.sdk.ui.views.verification.VerificationView;


public class PhysicalCardActivationActivity extends FragmentMvpActivity<PhysicalCardActivationModel,
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
    protected void onStart() {
        super.onStart();
        Drawable closeIcon = ContextCompat.getDrawable(this, R.drawable.ic_close);
        closeIcon.setColorFilter(UIStorage.getInstance().getIconTertiaryColor(), PorterDuff.Mode.SRC_ATOP);
        mView.getToolbar().setNavigationIcon(closeIcon);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = true;

        int i = item.getItemId();
        if (i == android.R.id.home) {
            mPresenter.onBack();
        } else {
            handled = false;
        }

        return handled || super.onOptionsItemSelected(item);
    }
}
