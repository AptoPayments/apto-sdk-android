package com.aptopayments.core.repository.voip.remote.entities

import com.aptopayments.core.data.voip.VoipCall
import com.google.gson.annotations.SerializedName

internal data class VoipCallEntity(

    @SerializedName("access_token")
    val accessToken: String = "",

    @SerializedName("request_token")
    val requestToken: String = "",

    @SerializedName("provider")
    val provider: String = ""

) {
    fun toVoipCall() = VoipCall(
        accessToken = accessToken,
        requestToken = requestToken,
        provider = provider
    )
}
