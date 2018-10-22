package com.shiftpayments.link.sdk.ui.models.card;

import android.app.Activity;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;

/**
 * Concrete {@link Model} for the physical card activation screen
 * @author Adrian
 */
public class PhysicalCardActivationModel implements ActivityModel {

    private String mActivationCode;

    public PhysicalCardActivationModel() { }

    public String getActivationCode() {
        return mActivationCode;
    }

    public void setActivationCode(String activationCode) {
        mActivationCode = activationCode;
    }

    public boolean hasActivationCode() {
        return mActivationCode != null && !mActivationCode.isEmpty();
    }

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