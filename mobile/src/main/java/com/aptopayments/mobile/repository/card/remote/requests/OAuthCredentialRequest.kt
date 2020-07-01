package com.aptopayments.mobile.repository.card.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class OAuthCredentialRequest(
    @SerializedName("type")
    val type: String = "custodian",

    @SerializedName("credential_type")
    val credentialType: String = "oauth",

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String
) : Serializable
