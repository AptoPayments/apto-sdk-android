package com.shift.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import com.shift.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shift.link.sdk.api.vos.responses.workflow.SelectFundingAccountConfigurationVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;

import java.util.ArrayList;

/**
 * Created by adrian on 17/01/2017.
 */

public class AddFinancialAccountListModel implements ActivityModel, Model {

    private SelectFundingAccountConfigurationVo mConfig;

    public AddFinancialAccountListModel(SelectFundingAccountConfigurationVo configuration) {
        mConfig = configuration;
    }

    @Override
    public int getActivityTitleResource() {
        return R.string.add_financial_account_list_title;
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
     * @return The list of required actions.
     */
    public ArrayList<FinancialAccountVo.FinancialAccountType> getFinancialAccountTypes() {
        ArrayList<FinancialAccountVo.FinancialAccountType> typesArray = new ArrayList<>();
        if(mConfig.isACHEnabled) {
            typesArray.add(FinancialAccountVo.FinancialAccountType.Bank);
        }
        if(mConfig.isCardEnabled) {
            typesArray.add(FinancialAccountVo.FinancialAccountType.Card);
        }
        if(mConfig.isVirtualCardEnabled) {
            typesArray.add(FinancialAccountVo.FinancialAccountType.VirtualCard);
        }
        return typesArray;
    }
}
