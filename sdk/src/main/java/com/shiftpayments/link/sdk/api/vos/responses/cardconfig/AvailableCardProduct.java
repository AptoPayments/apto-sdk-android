package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

import com.google.gson.annotations.SerializedName;

public class AvailableCardProduct {

    @SerializedName("type")
    public String type;

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

}
