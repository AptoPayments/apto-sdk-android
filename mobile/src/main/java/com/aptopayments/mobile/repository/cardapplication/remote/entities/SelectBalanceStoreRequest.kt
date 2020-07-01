package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class SelectBalanceStoreRequest(

    @SerializedName("oauth_token_id")
    val tokenId: String

) : Serializable
