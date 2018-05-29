package com.shiftpayments.link.sdk.ui.activities.custodianselector;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.StartOAuthResponseVo;
import com.shiftpayments.link.sdk.sdk.ShiftLinkSdk;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;
import com.shiftpayments.link.sdk.ui.presenters.custodianselector.CoinbaseDelegate;
import com.shiftpayments.link.sdk.ui.workflow.ModuleManager;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by adrian on 20/02/2018.
 */

public class CoinbaseActivity extends Activity {

    private CoinbaseDelegate mCurrentModule;
    private Thread mGetStatusThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ModuleManager.getInstance().getCurrentModule() instanceof CoinbaseDelegate) {
            mCurrentModule = (CoinbaseDelegate) ModuleManager.getInstance().getCurrentModule();
        } else {
            throw new NullPointerException("Received Module does not implement CoinbaseDelegate!");
        }
        ShiftLinkSdk.getResponseHandler().subscribe(this);
        ShiftPlatform.startOAuth("COINBASE");
    }

    /**
     * Called when the start oAuth response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(StartOAuthResponseVo response) {
        // TODO: open in webview instead
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.url));
        startActivity(browserIntent);
        getStatusPeriodically(response.id);
    }

    /**
     * Called when the start oAuth response has been received.
     * @param response API response.
     */
    @Subscribe
    public void handleResponse(OAuthStatusResponseVo response) {
        if(response.status.equals("passed")) {
            mGetStatusThread.interrupt();
            mCurrentModule.coinbaseTokensRetrieved(
                    response.tokens.access,
                    response.tokens.refresh);
            this.finish();
        }
    }

    /**
     * Called when an API error has been received.
     * @param error API error.
     */
    @Subscribe
    public void handleApiError(ApiErrorVo error) {
        mGetStatusThread.interrupt();
        mCurrentModule.onCoinbaseException(error);
    }

    private void getStatusPeriodically(String id) {
        Runnable getStatus = () -> ShiftPlatform.getOAuthStatus(id);
        mGetStatusThread = new Thread(() -> {
            try {
                while(true) {
                    Thread.sleep(5000);
                    getStatus.run();
                }
            } catch (InterruptedException e) {
                return;
            }
        });
        mGetStatusThread.start();

    }
}
