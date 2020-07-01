package com.aptopayments.mobile.repository.user.remote.requests

import com.google.gson.annotations.SerializedName

internal data class PushDeviceRequest(
    @SerializedName("device_type")
    val deviceType: String = "ANDROID",

    @SerializedName("push_token")
    val pushToken: String
)
