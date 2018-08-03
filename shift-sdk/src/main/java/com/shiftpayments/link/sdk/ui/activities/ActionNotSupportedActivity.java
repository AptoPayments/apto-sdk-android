package com.shiftpayments.link.sdk.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.views.ActionNotSupportedView;

/**
 * Activity shown when a workflow action that is not supported is received
 * @author Adrian
 */

public class ActionNotSupportedActivity extends BaseActivity {

    private ActionNotSupportedView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        mView.setErrorText(this.getString(R.string.action_not_supported_error));
    }

    private void setView() {
        mView = (ActionNotSupportedView) View.inflate(this, R.layout.act_action_not_supported, null);
        setContentView(mView);
    }
}
