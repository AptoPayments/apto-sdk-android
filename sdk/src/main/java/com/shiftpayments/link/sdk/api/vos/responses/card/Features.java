package com.shiftpayments.link.sdk.api.vos.responses.card;

import com.google.gson.annotations.SerializedName;

public class Features {

    @SerializedName("get_pin")
    public GetPin getPin;

    @SerializedName("set_pin")
    public SetPin setPin;

    @SerializedName("select_balance_store")
    public SelectBalanceStore selectBalanceStore;

    public Features(GetPin getPin, SetPin setPin, SelectBalanceStore selectBalanceStore) {
        super();
        this.getPin = getPin;
        this.setPin = setPin;
        this.selectBalanceStore = selectBalanceStore;
    }
}