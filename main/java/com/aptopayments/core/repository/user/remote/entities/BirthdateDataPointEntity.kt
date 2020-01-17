package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.BirthdateDataPoint
import com.aptopayments.core.extension.ISO8601
import com.aptopayments.core.extension.parseISO8601Date
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName

internal data class BirthdateDataPointEntity (

        @SerializedName("data_type")
        override val dataType: String = "birthdate",

        @SerializedName("verification")
        override val verification: VerificationEntity? = null,

        @SerializedName("verified")
        override val verified: Boolean? = false,

        @SerializedName("not_specified")
        override val notSpecified: Boolean? = false,

        @SerializedName("date")
        val birthdate: String = ""

) : DataPointEntity {
    override fun toDataPoint() = BirthdateDataPoint(
            verification = verification?.toVerification(),
            verified = verified,
            notSpecified = notSpecified,
            birthdate = parseISO8601Date(birthdate)
    )

    companion object {
        fun from(dataPoint: BirthdateDataPoint) = BirthdateDataPointEntity(
                verification = VerificationEntity.from(dataPoint.verification),
                verified = dataPoint.verified,
                notSpecified = dataPoint.notSpecified,
                birthdate = ISO8601.formatDate(dataPoint.birthdate)
        )
    }
}
