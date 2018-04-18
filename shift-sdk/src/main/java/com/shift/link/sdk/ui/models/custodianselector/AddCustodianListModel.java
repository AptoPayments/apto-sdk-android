package com.shift.link.sdk.ui.models.custodianselector;

import android.app.Activity;

import com.shift.link.sdk.api.vos.responses.workflow.SelectCustodianConfigurationVo;
import com.shift.link.sdk.ui.R;
import com.shift.link.sdk.ui.models.ActivityModel;
import com.shift.link.sdk.ui.models.Model;

import java.util.ArrayList;

/**
 * Created by adrian on 17/01/2017.
 */

public class AddCustodianListModel implements ActivityModel, Model {
    public enum CustodianType {
        Coinbase,
        Dwolla
    }

    private SelectCustodianConfigurationVo mConfig;

    public AddCustodianListModel(SelectCustodianConfigurationVo configuration) {
        mConfig = configuration;
    }

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

    /**
     * @return The list of required actions.
     */
    public ArrayList<AddCustodianListModel.CustodianType> getCustodianTypes() {
        ArrayList<AddCustodianListModel.CustodianType> typesArray = new ArrayList<>();
        if(mConfig.isCoinbaseEnabled) {
            typesArray.add(CustodianType.Coinbase);
        }
        if(mConfig.isDwollaEnabled) {
            typesArray.add(CustodianType.Dwolla);
        }
        return typesArray;
    }
}
