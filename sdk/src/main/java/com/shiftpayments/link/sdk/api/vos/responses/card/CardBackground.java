package com.shiftpayments.link.sdk.api.vos.responses.card;

public abstract class CardBackground {
    public String type;
    public String value;

    public CardBackground(String type, String value) {
        this.type = type;
        this.value = value;
    }
}
