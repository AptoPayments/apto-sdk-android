package com.aptopayments.core.repository.verification.remote.entities.request

import com.aptopayments.core.repository.user.remote.entities.DataPointEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class StartVerificationRequest(

        @SerializedName("datapoint_type")
        val datapointType: String,

        @SerializedName("datapoint")
        val datapoint: DataPointEntity,

        @SerializedName("show_verification_secret")
        val showVerificationSecret: Boolean = false

) : Serializable
