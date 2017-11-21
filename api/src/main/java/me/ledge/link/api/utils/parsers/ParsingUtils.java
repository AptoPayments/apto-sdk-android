package me.ledge.link.api.utils.parsers;

import com.google.gson.JsonElement;

/**
 * Created by adrian on 23/10/2017.
 */

class ParsingUtils {
    static String getStringFromJson(JsonElement element) {
        if(element.isJsonNull()) {
            return null;
        }

        return element.getAsString();
    }
}
