package com.shiftpayments.link.sdk.api.vos.responses.card;

import com.google.gson.annotations.SerializedName;

public class SetPin {

    @SerializedName("status")
    public String status;

    public SetPin() {
    }

    public SetPin(String status) {
        super();
        this.status = status;
    }
}
