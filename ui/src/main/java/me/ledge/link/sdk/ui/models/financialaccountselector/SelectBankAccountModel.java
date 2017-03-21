package me.ledge.link.sdk.ui.models.financialaccountselector;

import me.ledge.link.api.vos.datapoints.BankAccount;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.sdk.ui.R;

/**
 * Concrete {@link SelectFinancialAccountModel} for adding a bank account.
 * @author Adrian
 */
public class SelectBankAccountModel implements SelectFinancialAccountModel {

    private BankAccount mBankAccount;

    /**
     * Creates a new {@link SelectBankAccountModel} instance.
     * @param bankAccount
     */
    public SelectBankAccountModel(BankAccount bankAccount) {
        mBankAccount = bankAccount;
    }

    /** {@inheritDoc} */
    @Override
    public int getIconResourceId() {
        return R.drawable.icon_add_bank_account;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return mBankAccount.bankName + " (...." + mBankAccount.lastFourDigits + ")";
    }

    @Override
    public FinancialAccountVo getFinancialAccount() {
        return mBankAccount;
    }
}
