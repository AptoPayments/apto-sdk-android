package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.AddressDataPoint
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName

internal data class AddressDataPointEntity(

    @SerializedName("data_type")
    override val dataType: String = "address",

    @SerializedName("verification")
    override val verification: VerificationEntity? = null,

    @SerializedName("verified")
    override val verified: Boolean? = false,

    @SerializedName("not_specified")
    override val notSpecified: Boolean? = false,

    @SerializedName("street_one")
    val streetOne: String? = null,

    @SerializedName("street_two")
    val streetTwo: String? = null,

    @SerializedName("locality")
    val locality: String? = null,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("postal_code")
    val postalCode: String? = null,

    @SerializedName("country")
    val country: String? = null

) : DataPointEntity {
    override fun toDataPoint() = AddressDataPoint(
        streetOne = streetOne,
        streetTwo = streetTwo,
        locality = locality,
        region = region,
        postalCode = postalCode,
        country = country,
        verification = verification?.toVerification(),
        verified = verified,
        notSpecified = notSpecified
    )

    companion object {
        fun from(dataPoint: AddressDataPoint) = AddressDataPointEntity(
            verification = VerificationEntity.from(dataPoint.verification),
            verified = dataPoint.verified,
            notSpecified = dataPoint.notSpecified,
            streetOne = dataPoint.streetOne,
            streetTwo = dataPoint.streetTwo,
            locality = dataPoint.locality,
            region = dataPoint.region,
            postalCode = dataPoint.postalCode,
            country = dataPoint.country
        )
    }
}
