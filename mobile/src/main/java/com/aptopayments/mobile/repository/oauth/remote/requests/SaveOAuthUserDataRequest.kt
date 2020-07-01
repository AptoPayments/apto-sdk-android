package com.aptopayments.mobile.repository.oauth.remote.requests

import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.user.remote.entities.DataPointEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class SaveOAuthUserDataRequest(

    @SerializedName("provider")
    val provider: String,

    @SerializedName("oauth_token_id")
    val tokenId: String,

    @SerializedName("data_points")
    val userData: ListEntity<DataPointEntity>

) : Serializable
