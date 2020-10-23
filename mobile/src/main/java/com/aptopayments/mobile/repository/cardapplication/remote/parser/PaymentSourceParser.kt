package com.aptopayments.mobile.repository.cardapplication.remote.parser

import com.aptopayments.mobile.extension.safeStringFromJson
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.paymentsources.remote.entities.CardEntity
import com.aptopayments.mobile.repository.paymentsources.remote.entities.PaymentSourceEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

private const val TYPE_CARD = "card"

internal class PaymentSourceParser : JsonDeserializer<PaymentSourceEntity?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PaymentSourceEntity? {
        val configJson = json?.asJsonObject ?: return null
        return when (safeStringFromJson(configJson.get("type"))) {
            TYPE_CARD -> parseCard(configJson)
            else -> null
        }
    }

    private fun parseCard(configJson: JsonObject) =
        GsonProvider.provide().fromJson(configJson, CardEntity::class.java)
}
