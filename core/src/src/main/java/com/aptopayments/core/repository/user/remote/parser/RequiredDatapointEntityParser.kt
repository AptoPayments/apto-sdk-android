package com.aptopayments.core.repository.user.remote.parser

import com.aptopayments.core.extension.safeStringFromJson
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.user.remote.entities.AddressDataPointConfigurationEntity
import com.aptopayments.core.repository.user.remote.entities.DataPointConfigurationEntity
import com.aptopayments.core.repository.user.remote.entities.IdDataPointConfigurationEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

private const val ID_DOCUMENT = "id_document_datapoint_configuration"
private const val ADDRESS = "address_datapoint_configuration"

internal class RequiredDatapointEntityParser : JsonDeserializer<DataPointConfigurationEntity?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DataPointConfigurationEntity? {
        val datapointJson = json?.asJsonObject ?: return null
        return when (safeStringFromJson(datapointJson.get("type"))) {
            ID_DOCUMENT -> parseIdDataPoint(datapointJson)
            ADDRESS -> parseAddressDataPoint(datapointJson)
            else -> null
        }
    }

    private fun parseIdDataPoint(datapointJson: JsonObject) =
        ApiCatalog.gson().fromJson<IdDataPointConfigurationEntity>(
            datapointJson, IdDataPointConfigurationEntity::class.java
        )

    private fun parseAddressDataPoint(datapointJson: JsonObject) =
        ApiCatalog.gson().fromJson<AddressDataPointConfigurationEntity>(
            datapointJson, AddressDataPointConfigurationEntity::class.java
        )
}
