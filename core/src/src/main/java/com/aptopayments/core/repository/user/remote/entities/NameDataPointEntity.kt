package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.NameDataPoint
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName

internal data class NameDataPointEntity (

        @SerializedName("data_type")
        override val dataType: String = "name",

        @SerializedName("verification")
        override val verification: VerificationEntity? = null,

        @SerializedName("verified")
        override val verified: Boolean? = false,

        @SerializedName("not_specified")
        override val notSpecified: Boolean? = false,

        @SerializedName("first_name")
        val firstName: String = "",

        @SerializedName("last_name")
        val lastName: String = ""

) : DataPointEntity {
    override fun toDataPoint() = NameDataPoint(
        firstName = firstName,
        lastName = lastName,
        verification = verification?.toVerification(),
        verified = verified,
        notSpecified = notSpecified
    )

    companion object {
        fun from(dataPoint: NameDataPoint) = NameDataPointEntity(
                verification = VerificationEntity.from(dataPoint.verification),
                verified = dataPoint.verified,
                notSpecified = dataPoint.notSpecified,
                firstName = dataPoint.firstName,
                lastName = dataPoint.lastName
        )
    }
}
