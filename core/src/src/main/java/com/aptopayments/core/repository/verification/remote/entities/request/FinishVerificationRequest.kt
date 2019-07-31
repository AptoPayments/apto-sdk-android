package com.aptopayments.core.repository.verification.remote.entities.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FinishVerificationRequest(

        @SerializedName("secret")
        val secret: String

) : Serializable
