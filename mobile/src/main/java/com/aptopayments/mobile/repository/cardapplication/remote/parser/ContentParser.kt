package com.aptopayments.mobile.repository.cardapplication.remote.parser

import com.aptopayments.mobile.extension.safeStringFromJson
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction.*
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

internal class ContentParser : JsonDeserializer<ContentEntity?> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ContentEntity? {
        val contentJson = json?.asJsonObject ?: return null
        return when (safeStringFromJson(contentJson.get("format"))) {
            FORMAT_PLAIN_TEXT -> parsePlainContent(contentJson)
            FORMAT_MARKDOWN -> parsePlainContent(contentJson)
            FORMAT_EXTERNAL_URL -> parsePlainContent(contentJson)
            FORMAT_NATIVE_CONTENT -> parseNativeContent(contentJson)
            else -> null
        }
    }

    private fun parseNativeContent(contentJson: JsonObject): ContentEntity? {
        return GsonProvider.provide().fromJson(contentJson, NativeContentEntity::class.java)
    }

    private fun parsePlainContent(contentJson: JsonObject): ContentEntity? {
        return GsonProvider.provide().fromJson(contentJson, PlainTextContentEntity::class.java)
    }
}
