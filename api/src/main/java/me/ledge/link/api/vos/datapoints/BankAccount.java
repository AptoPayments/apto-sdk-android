package me.ledge.link.api.vos.datapoints;

/**
 * Created by adrian on 18/01/2017.
 */

public class BankAccount extends FinancialAccountVo {
    public String bankName;
    public String lastFourDigits;

    public BankAccount(String accountId, String bankName, String lastFourDigits, boolean verified,
                       boolean notSpecified) {
        super(accountId, FinancialAccountType.Bank, verified, notSpecified);
        this.bankName = bankName;
        this.lastFourDigits = lastFourDigits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankAccount that = (BankAccount) o;

        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null)
            return false;
        return lastFourDigits != null ? lastFourDigits.equals(that.lastFourDigits) : that.lastFourDigits == null;

    }

    @Override
    public int hashCode() {
        int result = bankName != null ? bankName.hashCode() : 0;
        result = 31 * result + (lastFourDigits != null ? lastFourDigits.hashCode() : 0);
        return result;
    }
}
