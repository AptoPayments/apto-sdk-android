package com.aptopayments.core.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

data class SetCardFundingSourceRequest(
        @SerializedName("funding_source_id")
        val fundingSourceId: String
)
