package com.aptopayments.mobile.extension

import com.google.gson.JsonElement

internal fun safeStringFromJson(element: JsonElement?): String? {
    return if (element == null || element.isJsonNull) {
        null
    } else element.asString
}
