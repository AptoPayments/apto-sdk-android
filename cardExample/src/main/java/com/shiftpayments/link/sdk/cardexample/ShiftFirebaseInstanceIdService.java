package com.shiftpayments.link.sdk.cardexample;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.shiftpayments.link.sdk.ui.ShiftPlatform;

public class ShiftFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public ShiftFirebaseInstanceIdService() {
        super();
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("ADRIAN", "Refreshed token: " + refreshedToken);

        ShiftPlatform.setFirebaseToken(refreshedToken);
    }
}
