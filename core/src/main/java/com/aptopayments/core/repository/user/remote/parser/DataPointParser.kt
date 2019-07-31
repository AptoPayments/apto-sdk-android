package com.aptopayments.core.repository.user.remote.parser

import com.aptopayments.core.extension.safeStringFromJson
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.user.remote.entities.*
import com.google.gson.*
import java.lang.reflect.Type

private const val NAME_DATAPOINT_TYPE = "name"
private const val PHONE_DATAPOINT_TYPE = "phone"
private const val EMAIL_DATAPOINT_TYPE = "email"
private const val BIRTHDATE_DATAPOINT_TYPE = "birthdate"
private const val ADDRESS_DATAPOINT_TYPE = "address"

internal class DataPointParser : JsonDeserializer<DataPointEntity?>, JsonSerializer<DataPointEntity> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): DataPointEntity? {
        val datapointJson= json?.asJsonObject ?: return null
        return when (safeStringFromJson(datapointJson.get("type"))) {
            NAME_DATAPOINT_TYPE -> parseNameDataPoint(datapointJson)
            PHONE_DATAPOINT_TYPE -> parsePhoneDataPoint(datapointJson)
            EMAIL_DATAPOINT_TYPE -> parseEmailDataPoint(datapointJson)
            BIRTHDATE_DATAPOINT_TYPE -> parseBirthdateDataPoint(datapointJson)
            ADDRESS_DATAPOINT_TYPE -> parseAddressDataPoint(datapointJson)
            else -> null
        }
    }

    private fun parseNameDataPoint(configJson: JsonObject): NameDataPointEntity? {
        return ApiCatalog.gson().fromJson<NameDataPointEntity>(configJson,
                NameDataPointEntity::class.java)
    }

    private fun parsePhoneDataPoint(configJson: JsonObject): PhoneDataPointEntity? {
        return ApiCatalog.gson().fromJson<PhoneDataPointEntity>(configJson,
                PhoneDataPointEntity::class.java)
    }

    private fun parseEmailDataPoint(configJson: JsonObject): EmailDataPointEntity? {
        return ApiCatalog.gson().fromJson<EmailDataPointEntity>(configJson,
                EmailDataPointEntity::class.java)
    }

    private fun parseBirthdateDataPoint(configJson: JsonObject): BirthdateDataPointEntity? {
        return ApiCatalog.gson().fromJson<BirthdateDataPointEntity>(configJson,
                BirthdateDataPointEntity::class.java)
    }

    private fun parseAddressDataPoint(configJson: JsonObject): AddressDataPointEntity? {
        return ApiCatalog.gson().fromJson<AddressDataPointEntity>(configJson,
                AddressDataPointEntity::class.java)
    }

    override fun serialize(src: DataPointEntity?,
                           typeOfSrc: Type?,
                           context: JsonSerializationContext?): JsonElement {
        return when (src) {
            is NameDataPointEntity -> serializeNameDataPointEntity(src)
            is PhoneDataPointEntity -> serializePhoneDataPointEntity(src)
            is EmailDataPointEntity -> serializeEmailDataPointEntity(src)
            is BirthdateDataPointEntity -> serializeBirthdateDataPointEntity(src)
            is AddressDataPointEntity -> serializeAddressDataPointEntity(src)
            else -> JsonNull.INSTANCE
        }
    }

    private fun serializeDataPointEntity(entity: DataPointEntity): JsonObject {
        val jsonObject = JsonObject().apply {
            addProperty("data_type", entity.dataType)
            addProperty("verified", entity.verified)
            addProperty("not_specified", entity.notSpecified)
        }
        entity.verification?.let {
            jsonObject.add("verification", ApiCatalog.gson().toJsonTree(it))
        }
        return jsonObject
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
}
