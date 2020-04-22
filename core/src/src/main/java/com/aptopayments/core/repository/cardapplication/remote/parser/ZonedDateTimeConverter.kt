package com.aptopayments.core.repository.cardapplication.remote.parser

import com.google.gson.*
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

class ZonedDateTimeConverter : JsonSerializer<ZonedDateTime?>, JsonDeserializer<ZonedDateTime?> {

    override fun serialize(src: ZonedDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ZonedDateTime {
        return FORMATTER.parse(json.asString, ZonedDateTime::from)
    }
}
