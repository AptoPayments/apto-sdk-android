package com.aptopayments.mobile.repository.voip.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class GetTokensRequest(
    @SerializedName("card_id")
    val cardID: String,
    @SerializedName("action")
    val action: String
) : Serializable
