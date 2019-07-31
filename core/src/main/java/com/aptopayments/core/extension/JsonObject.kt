package com.aptopayments.core.extension

import com.google.gson.JsonElement

fun safeStringFromJson(element: JsonElement?): String? {
    return if (element == null || element.isJsonNull) {
        null
    } else element.asString
}
