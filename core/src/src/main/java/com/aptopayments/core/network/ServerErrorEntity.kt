package com.aptopayments.core.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class ServerErrorEntity(

    @SerializedName("code")
    var code: Int? = null,

    @SerializedName("message")
    var message: String? = null

) : Serializable
