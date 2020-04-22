package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.EmailDataPoint
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName

internal data class EmailDataPointEntity (

        @SerializedName("data_type")
        override val dataType: String = "email",

        @SerializedName("verification")
        override val verification: VerificationEntity? = null,

        @SerializedName("verified")
        override val verified: Boolean? = false,

        @SerializedName("not_specified")
        override val notSpecified: Boolean? = false,

        @SerializedName("email")
        val email: String = ""

) : DataPointEntity {
    override fun toDataPoint() = EmailDataPoint (
        email = email,
        verification = verification?.toVerification(),
        verified = verified,
        notSpecified = notSpecified
    )

    companion object {
        fun from(dataPoint: EmailDataPoint) = EmailDataPointEntity(
                verification = VerificationEntity.from(dataPoint.verification),
                verified = dataPoint.verified,
                notSpecified = dataPoint.notSpecified,
                email = dataPoint.email
        )
    }
}
