package com.aptopayments.core.repository.user.remote.requests

import com.google.gson.annotations.SerializedName

data class PushDeviceRequest(
    @SerializedName("device_type")
    val deviceType: String = "ANDROID",

    @SerializedName("push_token")
    val pushToken: String
)
