package me.ledge.link.sdk.ui.models.financialaccountselector;

import android.app.Activity;

import me.ledge.link.sdk.ui.R;
import me.ledge.link.sdk.ui.models.ActivityModel;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a bank statement.
 * @author Adrian
 */
public class AddBankAccountModel implements AddFinancialAccountModel, ActivityModel {

    /**
     * Creates a new {@link AddBankAccountModel} instance.
     */
    public AddBankAccountModel() {

    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_bank_account;
    }

    /** {@inheritDoc} */
    @Override
    public int getTitleResourceId() {
        return R.string.add_financial_account_bank;
    }

    @Override
    public int getDescription() {
        return R.string.add_financial_account_bank_description;
    }

    @Override
    public int getActivityTitleResource() {
        return R.string.add_financial_account_bank_title;
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
