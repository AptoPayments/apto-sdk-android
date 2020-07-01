package com.aptopayments.mobile.repository.card.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class IssueCardRequest(
    @SerializedName("type")
    val type: String = "card",

    @SerializedName("card_product_id")
    val cardProductId: String,

    @SerializedName("balance_store")
    val oAuthCredentialRequest: OAuthCredentialRequest? = null,

    @SerializedName("additional_fields")
    val additionalFields: Map<String, Any>? = null,

    @SerializedName("initial_funding_source_id")
    val initialFundingSourceId: String? = null
) : Serializable
