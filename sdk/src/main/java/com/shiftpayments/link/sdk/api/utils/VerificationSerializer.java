package com.shiftpayments.link.sdk.api.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.shiftpayments.link.sdk.api.vos.datapoints.VerificationVo;

import java.lang.reflect.Type;

/**
 * Created by adrian on 25/01/2017.
 */

public class VerificationSerializer implements JsonSerializer<VerificationVo>{
    @SuppressWarnings("RedundantCast")
    @Override
    public JsonElement serialize(VerificationVo src, Type typeOfSrc, JsonSerializationContext context) {
        // Cast to Object required due to Gson library limitations: https://stackoverflow.com/a/15016251
        return context.serialize( (Object) src.toJSON());
    }
}
