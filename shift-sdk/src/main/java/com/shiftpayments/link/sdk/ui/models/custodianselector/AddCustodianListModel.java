package com.shiftpayments.link.sdk.ui.models.custodianselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;

/**
 * Created by adrian on 17/01/2017.
 */

public class AddCustodianListModel implements ActivityModel, Model {

    @Override
    public int getActivityTitleResource() {
        return R.string.add_custodian_list_title;
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
