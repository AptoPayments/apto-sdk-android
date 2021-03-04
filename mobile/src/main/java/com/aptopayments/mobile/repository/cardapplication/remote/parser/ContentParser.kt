package com.aptopayments.mobile.repository.cardapplication.remote.parser

import com.aptopayments.mobile.extension.safeStringFromJson
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction.*
import com.google.gson.*
import java.lang.reflect.Type

internal class ContentParser : JsonDeserializer<ContentEntity?>, JsonSerializer<ContentEntity> {
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

    override fun serialize(src: ContentEntity?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        return src?.let {
            when (it) {
                is NativeContentEntity -> createNativeContent(it)
                is PlainTextContentEntity -> createPlainText(it)
                else -> null
            }
        }
    }

    private fun createPlainText(src: PlainTextContentEntity): JsonElement? {
        return JsonObject().apply {
            addProperty("format", src.format)
            addProperty("value", src.value)
        }
    }

    private fun createNativeContent(src: NativeContentEntity): JsonElement? {
        return JsonObject().apply {
            addProperty("format", src.format)
            add("value", GsonProvider.provide().toJsonTree(src.value))
        }
    }
}
