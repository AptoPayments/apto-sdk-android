package com.aptopayments.mobile.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

internal data class SetCardFundingSourceRequest(
    @SerializedName("funding_source_id")
    val fundingSourceId: String
)
