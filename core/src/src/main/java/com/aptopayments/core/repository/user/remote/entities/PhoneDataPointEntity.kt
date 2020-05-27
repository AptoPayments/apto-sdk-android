package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.PhoneNumber
import com.aptopayments.core.data.user.PhoneDataPoint
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class PhoneDataPointEntity(

    @SerializedName("data_type")
    override val dataType: String = "phone",

    @SerializedName("verification")
    override val verification: VerificationEntity? = null,

    @SerializedName("verified")
    override val verified: Boolean? = false,

    @SerializedName("not_specified")
    override val notSpecified: Boolean? = false,

    @SerializedName("country_code")
    val countryCode: String = "",

    @SerializedName("phone_number")
    val phoneNumber: String = ""

) : DataPointEntity, Serializable {
    override fun toDataPoint() = PhoneDataPoint(
        phoneNumber = PhoneNumber(countryCode = countryCode, phoneNumber = phoneNumber),
        verification = verification?.toVerification(),
        verified = verified,
        notSpecified = notSpecified
    )

    companion object {
        fun from(dataPoint: PhoneDataPoint) = PhoneDataPointEntity(
            verification = VerificationEntity.from(dataPoint.verification),
            verified = dataPoint.verified,
            notSpecified = dataPoint.notSpecified,
            countryCode = dataPoint.phoneNumber.countryCode,
            phoneNumber = dataPoint.phoneNumber.phoneNumber
        )
    }
}
