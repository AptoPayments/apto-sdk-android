package com.aptopayments.mobile.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

internal data class SetPinRequest(
    @SerializedName("pin")
    val pin: String
)
