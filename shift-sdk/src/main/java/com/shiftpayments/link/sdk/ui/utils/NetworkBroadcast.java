package com.shiftpayments.link.sdk.ui.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shiftpayments.link.sdk.api.utils.NetworkDelegate;

public class NetworkBroadcast extends BroadcastReceiver {

    private NetworkDelegate mDelegate;

    public NetworkBroadcast(NetworkDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(mDelegate!=null) {
            mDelegate.onNetworkStatusChanged(isConnectedToInternet(context));
        }
    }

    boolean isConnectedToInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        return serviceManager.isNetworkAvailable();
    }

}
