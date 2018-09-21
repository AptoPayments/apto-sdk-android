package com.shiftpayments.link.sdk.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.sdk.ShiftSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.views.SystemMaintenanceView;

/**
 * Activity shown when the backend is down for maintenance
 * @author Adrian
 */

public class SystemMaintenanceActivity extends BaseActivity
        implements SystemMaintenanceView.ViewListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemMaintenanceView view = (SystemMaintenanceView) View.inflate(this, R.layout.act_system_maintenance, null);
        view.setViewListener(this);
        setContentView(view);
    }

    @Override
    public void tryAgainClickHandler() {
        ShiftSdk.getApiWrapper().executePendingApiCalls();
        this.finish();
    }
}
