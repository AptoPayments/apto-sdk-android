package com.shiftpayments.link.sdk.api.vos.responses.card;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.AllowedBalanceType;

import java.util.ArrayList;

public class SelectBalanceStore {

    @SerializedName("allowed_balance_types")
    public ArrayList<AllowedBalanceType> allowedBalanceTypes;

    public SelectBalanceStore(ArrayList<AllowedBalanceType> allowedBalanceTypes) {
        this.allowedBalanceTypes = allowedBalanceTypes;
    }
}
