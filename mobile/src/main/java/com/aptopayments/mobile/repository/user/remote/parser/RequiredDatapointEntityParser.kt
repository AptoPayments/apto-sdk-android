package com.aptopayments.mobile.repository.user.remote.parser

import com.aptopayments.mobile.extension.safeStringFromJson
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.user.remote.entities.AllowedCountriesDataPointConfigurationEntity
import com.aptopayments.mobile.repository.user.remote.entities.DataPointConfigurationEntity
import com.aptopayments.mobile.repository.user.remote.entities.IdDataPointConfigurationEntity
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
        GsonProvider.provide().fromJson(datapointJson, IdDataPointConfigurationEntity::class.java)

    private fun parseAddressDataPoint(datapointJson: JsonObject) =
        GsonProvider.provide().fromJson(datapointJson, AllowedCountriesDataPointConfigurationEntity::class.java)
}
