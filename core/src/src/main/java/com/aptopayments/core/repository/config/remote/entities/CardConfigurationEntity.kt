package com.aptopayments.core.repository.config.remote.entities

import com.google.gson.annotations.SerializedName

internal data class CardConfigurationEntity(

        @SerializedName("card_product")
        val cardProduct: CardProductEntity = CardProductEntity()

) {
    fun toCardProduct() = cardProduct.toCardProduct()
}
