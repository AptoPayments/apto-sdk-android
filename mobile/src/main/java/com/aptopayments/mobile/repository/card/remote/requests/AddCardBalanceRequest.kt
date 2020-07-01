package com.aptopayments.mobile.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

internal data class AddCardBalanceRequest(
    @SerializedName("funding_source_type")
    val fundingSourceType: String,

    @SerializedName("oauth_token_id")
    val tokenId: String
)
