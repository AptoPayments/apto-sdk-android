package com.aptopayments.core.repository.verification.remote.entities.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class RestartVerificationRequest(

    @SerializedName("show_verification_secret")
    val showVerificationSecret: Boolean = false

) : Serializable
