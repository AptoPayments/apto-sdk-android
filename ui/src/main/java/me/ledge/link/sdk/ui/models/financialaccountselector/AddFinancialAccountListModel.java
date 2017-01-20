package me.ledge.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import me.ledge.link.api.vos.FinancialAccountVo;
import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;
import me.ledge.link.sdk.ui.models.Model;

import static me.ledge.link.api.vos.FinancialAccountVo.FinancialAccountType.Bank;
import static me.ledge.link.api.vos.FinancialAccountVo.FinancialAccountType.Card;
import static me.ledge.link.api.vos.FinancialAccountVo.FinancialAccountType.VirtualCard;

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
    public FinancialAccountVo.FinancialAccountType[] getFinancialAccountTypes() {
        // TODO: read from config file
        FinancialAccountVo.FinancialAccountType[] types = new FinancialAccountVo.FinancialAccountType[]{Bank, Card, VirtualCard};
        return types;
    }
}
