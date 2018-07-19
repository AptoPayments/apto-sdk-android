package com.shiftpayments.link.sdk.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.shiftpayments.link.sdk.api.vos.responses.NoConnectionErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.SystemMaintenanceVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;

import org.greenrobot.eventbus.Subscribe;

/**
 * Base Activity for those who do not implement MVP
 * @author Adrian
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        ShiftLinkSdk.getResponseHandler().subscribe(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShiftLinkSdk.getResponseHandler().unsubscribe(this);
    }

    /**
     * Called when there is no internet connection
     * @param error Error
     */
    @Subscribe
    public void handleResponse(NoConnectionErrorVo error) {
        this.startActivity(new Intent(this, NoConnectionActivity.class));
    }

    /**
     * Called when the backend is down for maintenance
     * @param error Error
     */
    @Subscribe
    public void handleResponse(SystemMaintenanceVo error) {
        this.startActivity(new Intent(this, SystemMaintenanceActivity.class));
    }
}
