package com.aptopayments.core.repository.card.remote.requests

import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
data class IssueCardRequest(
        @SerializedName("type")
        val type: String = "card",

        @SerializedName("card_product_id")
        val cardProductId: String,

        @SerializedName("balance_version")
        val balanceVersion: String,

        @SerializedName("balance_store")
        val oAuthCredentialRequest: OAuthCredentialRequest? = null
) : Serializable
