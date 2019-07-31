package com.aptopayments.core.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

data class SetPinRequest(
        @SerializedName("pin")
        val pin: String
)
