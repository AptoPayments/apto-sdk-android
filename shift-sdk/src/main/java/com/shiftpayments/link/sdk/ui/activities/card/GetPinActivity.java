package com.shiftpayments.link.sdk.ui.activities.card;

import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.models.card.GetPinModel;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.GetPinDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.GetPinPresenter;
import com.shiftpayments.link.sdk.ui.views.card.GetPinView;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

public class GetPinActivity extends FragmentMvpActivity<GetPinModel, GetPinView, GetPinPresenter> {

    @Override
    protected GetPinView createView() {
        return (GetPinView) View.inflate(this, R.layout.act_get_pin, null);
    }

    @Override
    protected GetPinPresenter createPresenter(BaseDelegate delegate) {
        if(ModuleManager.getInstance().getCurrentModule() instanceof GetPinDelegate) {
            return new GetPinPresenter(this, (GetPinDelegate) ModuleManager.getInstance().getCurrentModule());
        }
        else {
            throw new NullPointerException("Received Module does not implement GetPinDelegate!");
        }
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
