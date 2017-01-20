package me.ledge.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

/**
 * Created by adrian on 17/01/2017.
 */

public class AddFinancialAccountListModel implements ActivityModel, Model {

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
    public String[] getFinancialAccountTypes() {
        // TODO: read from config file
        String[] types = new String[]{"Bank", "Card", "VirtualCard"};
        return types;
    }
}
