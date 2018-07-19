package com.shiftpayments.link.sdk.ui.activities;

import android.os.Bundle;
import android.view.View;

import com.shiftpayments.link.sdk.api.vos.responses.ConnectionEstablishedVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.views.NoConnectionView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Activity shown when there is no internet connection
 * @author Adrian
 */

public class NoConnectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoConnectionView view = (NoConnectionView) View.inflate(this, R.layout.act_no_connection, null);
        setContentView(view);
    }

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
     * Called when the connection has been reestablished
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(ConnectionEstablishedVo response) {
        this.finish();
    }
}
