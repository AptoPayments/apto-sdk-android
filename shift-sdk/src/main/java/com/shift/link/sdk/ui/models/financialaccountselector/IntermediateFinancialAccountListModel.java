package com.shift.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;

/**
 * Created by adrian on 17/01/2017.
 */

public class IntermediateFinancialAccountListModel implements Model, ActivityModel {

    @Override
    public int getActivityTitleResource() {
        return R.string.select_financial_account_list_title;
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
