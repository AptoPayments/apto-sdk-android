package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.sdk.ui.R;

/**
 * Concrete {@link AddFinancialAccountModel} for adding a bank statement.
 * @author Adrian
 */
public class AddBankAccountModel implements AddFinancialAccountModel {

    /**
     * Creates a new {@link AddBankAccountModel} instance.
     */
    public AddBankAccountModel() {

    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_doc_bank_statement;
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
}
