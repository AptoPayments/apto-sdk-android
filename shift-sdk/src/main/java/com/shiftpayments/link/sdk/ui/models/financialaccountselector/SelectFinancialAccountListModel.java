package com.shiftpayments.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.ui.R;
import com.shiftpayments.link.sdk.ui.models.ActivityModel;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.storages.UserStorage;

import java.util.List;

/**
 * Created by adrian on 17/01/2017.
 */

public class SelectFinancialAccountListModel implements ActivityModel, Model {

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

    /**
     * @return The list of financial accounts.
     */
    public List<DataPointVo> getFinancialAccounts() {
        DataPointList userData = UserStorage.getInstance().getUserData();
        return userData.getDataPointsOf(DataPointVo.DataPointType.FinancialAccount);
    }
}
