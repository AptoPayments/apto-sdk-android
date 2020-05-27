package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.IdDocumentDataPoint
import com.aptopayments.core.data.user.IdDocumentDataPoint.Type
import com.aptopayments.core.repository.verification.remote.entities.VerificationEntity
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class IdDocumentDataPointEntity(
    @SerializedName("data_type")
    override val dataType: String = "id_document",

    @SerializedName("verification")
    override val verification: VerificationEntity? = null,

    @SerializedName("verified")
    override val verified: Boolean? = false,

    @SerializedName("not_specified")
    override val notSpecified: Boolean? = false,

    @SerializedName("doc_type")
    val type: String? = null,

    @SerializedName("value")
    val value: String? = null,

    @SerializedName("country")
    val country: String? = null
) : DataPointEntity {
    override fun toDataPoint() = IdDocumentDataPoint(
        type = type?.let { parseDocumentType(it) },
        value = value,
        country = country,
        verification = verification?.toVerification(),
        verified = verified,
        notSpecified = notSpecified
    )

    private fun parseDocumentType(type: String): Type? {
        return try {
            Type.valueOf(type.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            null
        }
    }

    companion object {
        fun from(dataPoint: IdDocumentDataPoint) = IdDocumentDataPointEntity(
            verification = VerificationEntity.from(dataPoint.verification),
            verified = dataPoint.verified,
            notSpecified = dataPoint.notSpecified,
            type = dataPoint.type.toString(),
            value = dataPoint.value,
            country = dataPoint.country
        )
    }
}
