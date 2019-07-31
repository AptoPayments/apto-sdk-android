package com.aptopayments.core.repository.card.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ActivatePhysicalCardRequest(

        @SerializedName("code")
        val code: String

) : Serializable
