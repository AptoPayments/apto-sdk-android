package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by adrian on 23/10/2017.
 */

class ParsingUtils {
    static String getStringFromJson(JsonElement element) {
        if(element == null || element.isJsonNull()) {
            return null;
        }

        return element.getAsString();
    }

    static JsonObject getJsonObject(JsonElement element) {
        if(element == null || element.isJsonNull()) {
            return null;
        }

        return element.getAsJsonObject();
    }

    static String getCurrencyStringFromJson(JsonElement element) {
        if(element == null || element.isJsonNull()) {
            return null;
        }

        return String.format(java.util.Locale.US,"%.2f", element.getAsFloat());
    }
}
