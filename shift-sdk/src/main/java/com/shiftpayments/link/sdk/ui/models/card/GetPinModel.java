package com.shiftpayments.link.sdk.ui.models.card;

import android.app.Activity;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;

public class GetPinModel implements ActivityModel {

    @Override
    public int getActivityTitleResource() {
        return R.string.physical_card_activation_title;
    }

    @Override
    public Class getPreviousActivity(Activity current) {
        return null;
    }

    @Override
    public Class getNextActivity(Activity current) {
        return null;
    }
}
