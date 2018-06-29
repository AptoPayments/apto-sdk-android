package com.shiftpayments.link.sdk.api.vos.requests.cardapplication;

import com.google.gson.annotations.SerializedName;

public class CreateCardApplicationRequestVo {

    @SerializedName("card_product_id")
    String cardProductId;

    public CreateCardApplicationRequestVo(String cardProductId) {
        this.cardProductId = cardProductId;
    }
}
