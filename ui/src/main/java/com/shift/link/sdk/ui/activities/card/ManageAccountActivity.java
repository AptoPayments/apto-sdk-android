package com.shift.link.sdk.ui.activities.card;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.shift.link.sdk.ui.presenters.card.ManageAccountPresenter;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.activities.FragmentMvpActivity;
import com.shift.link.sdk.ui.presenters.card.ManageAccountPresenter;
import com.shift.link.sdk.ui.views.card.ManageAccountView;


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
    protected ManageAccountPresenter createPresenter() {
        return new ManageAccountPresenter(this);
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
