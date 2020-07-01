package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class NewCardApplicationRequest(

    @SerializedName("card_product_id")
    val cardProductId: String

) : Serializable
