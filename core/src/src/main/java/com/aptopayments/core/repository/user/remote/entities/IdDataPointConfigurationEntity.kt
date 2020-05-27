package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.geo.Country
import com.aptopayments.core.data.user.IdDataPointConfiguration
import com.aptopayments.core.data.user.IdDocumentDataPoint
import com.google.gson.annotations.SerializedName

internal class IdDataPointConfigurationEntity : DataPointConfigurationEntity() {

    @SerializedName("allowed_document_types")
    var allowedDocumentTypes: HashMap<String, List<String>> = hashMapOf()

    override fun toDataPointConfiguration() =
        IdDataPointConfiguration(allowedDocumentTypes
            .mapKeys { Country(it.key) }
            .mapValues { value ->
                value.value.map { IdDocumentDataPoint.Type.fromString(it) }
            })
}
