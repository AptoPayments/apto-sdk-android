package com.aptopayments.mobile.repository.oauth.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class RetrieveOAuthUserDataRequest(

    @SerializedName("provider")
    val provider: String,

    @SerializedName("oauth_token_id")
    val tokenId: String

) : Serializable
