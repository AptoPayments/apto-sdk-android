package com.shift.link.sdk.ui.activities.financialaccountselector;

import android.content.Intent;
import android.view.View;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.MvpActivity;
import com.shift.link.sdk.ui.models.financialaccountselector.AddCardModel;
import com.shift.link.sdk.ui.presenters.financialaccountselector.AddCardDelegate;
import com.shift.link.sdk.ui.presenters.financialaccountselector.AddCardPresenter;
import com.shift.link.sdk.ui.presenters.userdata.BaseDelegate;
import com.shift.link.sdk.ui.views.financialaccountselector.AddCardView;

/**
 * Wires up the MVP pattern for the add card screen.
 * @author Adrian
 */

public class AddCardActivity
        extends MvpActivity<AddCardModel, AddCardView, AddCardPresenter> {

    /** {@inheritDoc} */
    @Override
    protected AddCardView createView() {
        return (AddCardView) View.inflate(this, R.layout.act_add_card, null);
    }

    /** {@inheritDoc} */
    @Override
    protected AddCardPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof AddCardDelegate) {
            return new AddCardPresenter(this, (AddCardDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement AddCardDelegate!");
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.handleRequestPermissionsResult(requestCode, grantResults);

    }
}
