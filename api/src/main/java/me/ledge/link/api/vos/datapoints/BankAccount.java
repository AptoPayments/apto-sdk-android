package me.ledge.link.api.vos.datapoints;

import me.ledge.link.api.vos.datapoints.FinancialAccountVo;

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
}
