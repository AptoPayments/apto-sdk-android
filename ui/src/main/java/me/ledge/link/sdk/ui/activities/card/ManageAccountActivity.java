package me.ledge.link.sdk.ui.activities.card;

import android.os.Bundle;
import android.view.View;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.activities.FragmentMvpActivity;
import me.ledge.link.sdk.ui.presenters.card.ManageAccountPresenter;
import me.ledge.link.sdk.ui.views.card.ManageAccountView;


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
}
