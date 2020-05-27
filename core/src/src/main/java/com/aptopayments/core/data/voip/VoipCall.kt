package com.aptopayments.core.data.voip

import java.io.Serializable

data class VoipCall(
    val accessToken: String,
    val requestToken: String,
    val provider: String
) : Serializable
