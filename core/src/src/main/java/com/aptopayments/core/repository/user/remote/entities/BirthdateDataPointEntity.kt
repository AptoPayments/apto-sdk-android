package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.BirthdateDataPoint
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDate

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
        birthdate = LocalDate.parse(birthdate),
        verification = verification?.toVerification(),
        verified = verified,
        notSpecified = notSpecified
    )

    companion object {
        fun from(dataPoint: BirthdateDataPoint) = BirthdateDataPointEntity(
                verification = VerificationEntity.from(dataPoint.verification),
                verified = dataPoint.verified,
                notSpecified = dataPoint.notSpecified,
                birthdate = dataPoint.birthdate.toString()
        )
    }
}
