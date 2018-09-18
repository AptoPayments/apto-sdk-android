package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.CustodianWalletVo;

/**
 * Created by adrian on 04/04/2018.
 */

public class BalanceVo {

    public String type;

    public String id;

    @SerializedName("funding_source_type")
    public String fundingSourceType;

    public MoneyVo balance;

    @SerializedName("amount_spendable")
    public MoneyVo amountSpendable;

    @SerializedName("amount_held")
    public MoneyVo amountHeld;

    @SerializedName("details")
    public CustodianWalletVo custodianWallet;
}
