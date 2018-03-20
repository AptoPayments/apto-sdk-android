package me.ledge.link.sdk.api.utils.parsers;

import com.google.gson.JsonElement;

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
}