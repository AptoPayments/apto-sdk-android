package com.shiftpayments.link.sdk.api.vos.responses.card;

import com.google.gson.annotations.SerializedName;

public class GetPin {

    @SerializedName("status")
    public String status;

    @SerializedName("type")
    public String type;

    @SerializedName("ivr_phone")
    public IvrPhone ivrPhone;

    public GetPin() {
    }

    public GetPin(String status, String type, IvrPhone ivrPhone) {
        super();
        this.status = status;
        this.type = type;
        this.ivrPhone = ivrPhone;
    }
}
