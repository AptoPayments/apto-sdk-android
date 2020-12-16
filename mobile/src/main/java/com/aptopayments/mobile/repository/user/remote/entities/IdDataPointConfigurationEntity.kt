package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.geo.Country
import com.aptopayments.mobile.data.user.IdDataPointConfiguration
import com.aptopayments.mobile.data.user.IdDocumentDataPoint
import com.google.gson.annotations.SerializedName

internal class IdDataPointConfigurationEntity : DataPointConfigurationEntity() {

    @SerializedName("allowed_document_types")
    val allowedDocumentTypes: HashMap<String, List<String>> = hashMapOf()

    override fun toDataPointConfiguration() =
        IdDataPointConfiguration(
            allowedDocumentTypes
                .mapKeys { Country(it.key) }
                .mapValues { value ->
                    value.value.map { IdDocumentDataPoint.Type.fromString(it) }
                }
        )
}
