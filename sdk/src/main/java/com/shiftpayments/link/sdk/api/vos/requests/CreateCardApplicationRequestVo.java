package com.shiftpayments.link.sdk.api.vos.requests;

import com.google.gson.annotations.SerializedName;

public class CreateCardApplicationRequestVo {

    @SerializedName("card_product_id")
    String cardProductId;

    public CreateCardApplicationRequestVo(String cardProductId) {
        this.cardProductId = cardProductId;
    }
}
