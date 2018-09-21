package com.shiftpayments.link.sdk.ui.models.card;

import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.ui.models.Model;
import com.shiftpayments.link.sdk.ui.vos.AmountVo;

/**
 * Concrete {@link Model} for the funding source.
 * @author Adrian
 */
public class BalanceModel implements Model {

    private final BalanceVo mBalance;
    private boolean mIsSelected;

    /**
     * Creates a new {@link BalanceModel} instance.
     * @param balance Raw balance data.
     * @param isSelected if it's the current balance
     */
    public BalanceModel(BalanceVo balance, boolean isSelected) {
        mBalance = balance;
        mIsSelected = isSelected;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        return other instanceof BalanceModel && ((BalanceModel) other).getBalanceId().equals(getBalanceId());
    }

    public String getBalanceName() {
        if(mBalance.custodianWallet.custodian.name != null && !mBalance.custodianWallet.custodian.name.isEmpty()) {
            return mBalance.custodianWallet.custodian.name.substring(0, 1).toUpperCase() + mBalance.custodianWallet.custodian.name.substring(1);
        }
        return "";
    }

    public String getBalanceAmount() {
        if(mBalance.balance.amount != null && mBalance.balance.currency!=null) {
            return new AmountVo(mBalance.balance.amount, mBalance.balance.currency).toString();
        }
        return "";
    }

    @Override
    public String toString() {
        Double amount = mBalance.custodianWallet.balance.amount == null ? 0 : mBalance.custodianWallet.balance.amount;
        String currency = mBalance.custodianWallet.balance.currency == null ? "" : mBalance.custodianWallet.balance.currency;
        return amount + " " + currency;
    }

    /**
     * @return Balance ID OR null if not found.
     */
    public String getBalanceId() {
        String id = null;

        if (mBalance != null) {
            id = mBalance.id;
        }

        return id;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public String getSpendableAmount() {
        return new AmountVo(mBalance.amountSpendable.amount, mBalance.amountSpendable.currency).toString();
    }
}
