package com.shiftpayments.link.sdk.api.vos.responses.card;

import com.google.gson.annotations.SerializedName;

public class CardStyle {
    @SerializedName("background")
    public CardBackground background;

    public CardStyle(CardBackground background) {
        this.background = background;
    }
}
