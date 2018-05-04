package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.datapoints.Custodian;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.MoneyVo;

/**
 * Custodian wallet data
 * @author Adrian
 */
public class CustodianWalletVo {
    public String type;
    public Custodian custodian;
    public MoneyVo balance;

    public CustodianWalletVo(String type, Custodian custodian, MoneyVo balance) {
        this.type = type;
        this.custodian = custodian;
        this.balance = balance;
    }
}
