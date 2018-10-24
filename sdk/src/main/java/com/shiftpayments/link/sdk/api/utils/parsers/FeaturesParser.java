package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.responses.card.Features;
import com.shiftpayments.link.sdk.api.vos.responses.card.GetPin;
import com.shiftpayments.link.sdk.api.vos.responses.card.IvrPhone;
import com.shiftpayments.link.sdk.api.vos.responses.card.SetPin;

import java.lang.reflect.Type;

public class FeaturesParser implements JsonDeserializer<Features> {

    @Override
    public Features deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();

        JsonObject getPinJson = jObject.get("get_pin").getAsJsonObject();
        String status = ParsingUtils.getStringFromJson(getPinJson.get("status"));
        String type = ParsingUtils.getStringFromJson(getPinJson.get("type"));
        JsonObject phoneJson = getPinJson.get("ivr_phone").getAsJsonObject();
        IvrPhone ivrPhone = new IvrPhone(phoneJson.get("country_code").getAsString(),
                phoneJson.get("phone_number").getAsString());
        GetPin getPin = new GetPin(status, type, ivrPhone);

        JsonObject setPinJson = jObject.get("set_pin").getAsJsonObject();
        SetPin setPin = new SetPin(ParsingUtils.getStringFromJson(setPinJson.get("status")));
        return new Features(getPin, setPin);
    }
}
