package com.aptopayments.core.repository.oauth.remote.requests

import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.user.remote.entities.DataPointEntity
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
