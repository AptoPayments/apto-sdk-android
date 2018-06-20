package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

/*
 * Card product configuration data
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class CardConfigResponseVo {

    @SerializedName("type")
    public String type;

    @SerializedName("card_product")
    public CardProductVo cardProduct;

    // TODO: check if required
    @SerializedName("available_card_products")
    public AvailableCardProductListResponseVo availableCardProducts;
}