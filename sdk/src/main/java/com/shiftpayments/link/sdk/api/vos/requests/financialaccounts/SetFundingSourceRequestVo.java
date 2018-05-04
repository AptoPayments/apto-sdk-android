package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.google.gson.annotations.SerializedName;

public class SetFundingSourceRequestVo {
    @SerializedName("funding_source_id")
    public String fundingSourceId;

    public SetFundingSourceRequestVo(String fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }
}
