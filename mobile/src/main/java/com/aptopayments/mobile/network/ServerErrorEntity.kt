package com.aptopayments.mobile.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class ServerErrorEntity(

    @SerializedName("code")
    val code: Int? = null,

    @SerializedName("message")
    val message: String? = null

) : Serializable
