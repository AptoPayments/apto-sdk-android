package com.shiftpayments.link.sdk.api.vos.responses.workflow;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceType;

public class AllowedBalanceType {

    @SerializedName("type")
    public String type;

    @SerializedName("balance_type")
    public BalanceType balanceType;

    @SerializedName("base_uri")
    public String baseUri;

    public AllowedBalanceType(String type, BalanceType balanceType, String baseUri) {
        super();
        this.type = type;
        this.balanceType = balanceType;
        this.baseUri = baseUri;
    }
}
