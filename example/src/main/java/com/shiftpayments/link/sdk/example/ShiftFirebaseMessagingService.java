package com.shiftpayments.link.sdk.example;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class ShiftFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            String toast = data.get("title") + '\n' + data.get("body");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toast.makeText(getApplicationContext(),
                    toast, Toast.LENGTH_LONG).show());
        }
    }

}
