package com.shiftpayments.link.sdk.cardexample;

import android.app.Application;
import io.branch.referral.Branch;

public class CardApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Branch logging for debugging
        Branch.enableLogging();

        // Branch object initialization
        Branch.getAutoInstance(this);
    }
}