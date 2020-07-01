package com.aptopayments.mobile.repository.user.remote.parser

import com.aptopayments.mobile.extension.safeStringFromJson
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.user.remote.entities.*
import com.google.gson.*
import java.lang.reflect.Type

private const val NAME_DATAPOINT_TYPE = "name"
private const val PHONE_DATAPOINT_TYPE = "phone"
private const val EMAIL_DATAPOINT_TYPE = "email"
private const val BIRTHDATE_DATAPOINT_TYPE = "birthdate"
private const val ADDRESS_DATAPOINT_TYPE = "address"
private const val ID_DOCUMENT_DATAPOINT_TYPE = "id_document"

internal class DataPointParser : JsonDeserializer<DataPointEntity?>, JsonSerializer<DataPointEntity> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DataPointEntity? {
        val datapointJson = json?.asJsonObject ?: return null
        return when (safeStringFromJson(datapointJson.get("type"))) {
            NAME_DATAPOINT_TYPE -> parseNameDataPoint(datapointJson)
            PHONE_DATAPOINT_TYPE -> parsePhoneDataPoint(datapointJson)
            EMAIL_DATAPOINT_TYPE -> parseEmailDataPoint(datapointJson)
            BIRTHDATE_DATAPOINT_TYPE -> parseBirthdateDataPoint(datapointJson)
            ADDRESS_DATAPOINT_TYPE -> parseAddressDataPoint(datapointJson)
            ID_DOCUMENT_DATAPOINT_TYPE -> parseIdDocumentDataPoint(datapointJson)
            else -> null
        }
    }

    private fun parseNameDataPoint(configJson: JsonObject): NameDataPointEntity? {
        return GsonProvider.provide().fromJson(configJson, NameDataPointEntity::class.java)
    }

    private fun parsePhoneDataPoint(configJson: JsonObject): PhoneDataPointEntity? {
        return GsonProvider.provide().fromJson(configJson, PhoneDataPointEntity::class.java)
    }

    private fun parseEmailDataPoint(configJson: JsonObject): EmailDataPointEntity? {
        return GsonProvider.provide().fromJson(configJson, EmailDataPointEntity::class.java)
    }

    private fun parseBirthdateDataPoint(configJson: JsonObject): BirthdateDataPointEntity? {
        return GsonProvider.provide().fromJson(configJson, BirthdateDataPointEntity::class.java)
    }

    private fun parseAddressDataPoint(configJson: JsonObject): AddressDataPointEntity? {
        return GsonProvider.provide().fromJson(configJson, AddressDataPointEntity::class.java)
    }

    private fun parseIdDocumentDataPoint(configJson: JsonObject): IdDocumentDataPointEntity? {
        return GsonProvider.provide().fromJson(configJson, IdDocumentDataPointEntity::class.java)
    }

    override fun serialize(
        src: DataPointEntity?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return when (src) {
            is NameDataPointEntity -> serializeNameDataPointEntity(src)
            is PhoneDataPointEntity -> serializePhoneDataPointEntity(src)
            is EmailDataPointEntity -> serializeEmailDataPointEntity(src)
            is BirthdateDataPointEntity -> serializeBirthdateDataPointEntity(src)
            is AddressDataPointEntity -> serializeAddressDataPointEntity(src)
            is IdDocumentDataPointEntity -> serializeIdDocumentDataPointEntity(src)
            else -> JsonNull.INSTANCE
        }
    }

    private fun serializeDataPointEntity(entity: DataPointEntity): JsonObject {
        return JsonObject().apply {
            addProperty("data_type", entity.dataType)
            addProperty("verified", entity.verified)
            entity.verification?.let {
                add("verification", GsonProvider.provide().toJsonTree(it))
            }
            addProperty("not_specified", entity.notSpecified)
        }
    }

    private fun serializeNameDataPointEntity(entity: NameDataPointEntity): JsonElement {
        return serializeDataPointEntity(entity).apply {
            addProperty("first_name", entity.firstName)
            addProperty("last_name", entity.lastName)
        }
    }

    private fun serializePhoneDataPointEntity(entity: PhoneDataPointEntity): JsonElement {
        return serializeDataPointEntity(entity).apply {
            addProperty("country_code", entity.countryCode)
            addProperty("phone_number", entity.phoneNumber)
        }
    }

    private fun serializeEmailDataPointEntity(entity: EmailDataPointEntity): JsonElement {
        return serializeDataPointEntity(entity).apply {
            addProperty("email", entity.email)
        }
    }

    private fun serializeBirthdateDataPointEntity(entity: BirthdateDataPointEntity): JsonElement {
        return serializeDataPointEntity(entity).apply {
            addProperty("date", entity.birthdate)
        }
    }

    private fun serializeAddressDataPointEntity(entity: AddressDataPointEntity): JsonElement {
        return serializeDataPointEntity(entity).apply {
            addProperty("street_one", entity.streetOne)
            addProperty("street_two", entity.streetTwo)
            addProperty("locality", entity.locality)
            addProperty("region", entity.region)
            addProperty("postal_code", entity.postalCode)
            addProperty("country", entity.country)
        }
    }

    private fun serializeIdDocumentDataPointEntity(entity: IdDocumentDataPointEntity): JsonElement {
        return serializeDataPointEntity(entity).apply {
            entity.country?.let { addProperty("country", entity.country) }
            entity.value?.let { addProperty("value", entity.value) }
            entity.type?.let { addProperty("doc_type", entity.type) }
            entity.notSpecified?.let { addProperty("not_specified", entity.notSpecified) }
        }
    }
}
