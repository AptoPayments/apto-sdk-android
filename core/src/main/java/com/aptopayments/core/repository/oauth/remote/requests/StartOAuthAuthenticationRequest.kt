package com.aptopayments.core.repository.oauth.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StartOAuthAuthenticationRequest(

        @SerializedName("provider")
        val provider: String,

        @SerializedName("base_uri")
        val baseUri: String,

        @SerializedName("redirect_url")
        val redirectUrl: String

) : Serializable
