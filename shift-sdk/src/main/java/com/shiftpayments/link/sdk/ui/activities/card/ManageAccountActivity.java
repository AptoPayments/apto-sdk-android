package com.shiftpayments.link.sdk.ui.activities.card;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.activities.FragmentMvpActivity;
import com.shiftpayments.link.sdk.ui.presenters.BaseDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.ManageAccountDelegate;
import com.shiftpayments.link.sdk.ui.presenters.card.ManageAccountPresenter;
import com.shiftpayments.link.sdk.ui.views.card.ManageAccountView;


/**
 * Created by adrian on 27/11/2017.
 */

public class ManageAccountActivity extends FragmentMvpActivity {

    /** {@inheritDoc} */
    @Override
    protected ManageAccountView createView() {
        return (ManageAccountView) View.inflate(this, R.layout.act_manage_account, null);
    }

    /** {@inheritDoc} */
    @Override
    protected ManageAccountPresenter createPresenter(BaseDelegate delegate) {
        if(delegate instanceof ManageAccountDelegate) {
            return new ManageAccountPresenter(this, (ManageAccountDelegate) delegate);
        }
        else {
            throw new NullPointerException("Received Module does not implement ManageAccountDelegate!");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
