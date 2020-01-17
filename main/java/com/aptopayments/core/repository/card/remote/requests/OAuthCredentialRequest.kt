package com.aptopayments.core.repository.card.remote.requests

import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
data class OAuthCredentialRequest (
        @SerializedName("type")
        val type: String = "custodian",

        @SerializedName("credential_type")
        val credentialType: String = "oauth",

        @SerializedName("access_token")
        val accessToken: String,

        @SerializedName("refresh_token")
        val refreshToken: String
) : Serializable
