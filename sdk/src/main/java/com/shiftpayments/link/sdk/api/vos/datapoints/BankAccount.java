package com.shiftpayments.link.sdk.api.vos.datapoints;

/**
 * Created by adrian on 18/01/2017.
 */

public class BankAccount extends FinancialAccountVo {
    public String bankName;
    public String lastFourDigits;

    public BankAccount(String accountId, String bankName, String lastFourDigits, boolean verified) {
        super(accountId, FinancialAccountType.Bank, verified);
        this.bankName = bankName;
        this.lastFourDigits = lastFourDigits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        BankAccount that = (BankAccount) o;

        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null)
            return false;
        return lastFourDigits != null ? lastFourDigits.equals(that.lastFourDigits) : that.lastFourDigits == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode()+(bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (lastFourDigits != null ? lastFourDigits.hashCode() : 0);
        return result;
    }
}
