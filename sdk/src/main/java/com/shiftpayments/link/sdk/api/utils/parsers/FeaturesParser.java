package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.responses.card.Features;
import com.shiftpayments.link.sdk.api.vos.responses.card.GetPin;
import com.shiftpayments.link.sdk.api.vos.responses.card.IvrPhone;
import com.shiftpayments.link.sdk.api.vos.responses.card.SelectBalanceStore;
import com.shiftpayments.link.sdk.api.vos.responses.card.SetPin;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.AllowedBalanceType;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.AllowedBalancesTypesList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class FeaturesParser implements JsonDeserializer<Features> {

    @Override
    public Features deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        if(ParsingUtils.getJsonObject(json) == null) {
            return null;
        }
        JsonObject jObject = json.getAsJsonObject();

        JsonObject getPinJson = jObject.get("get_pin").getAsJsonObject();
        String status = ParsingUtils.getStringFromJson(getPinJson.get("status"));
        String type = ParsingUtils.getStringFromJson(getPinJson.get("type"));
        JsonObject phoneJson = getPinJson.get("ivr_phone").getAsJsonObject();

        String countryCode = ParsingUtils.getStringFromJson(phoneJson.get("country_code"));
        String phoneNumber = ParsingUtils.getStringFromJson(phoneJson.get("phone_number"));
        IvrPhone ivrPhone = null;
        if(countryCode != null && phoneNumber != null) {
            ivrPhone = new IvrPhone(countryCode, phoneNumber);
        }
        GetPin getPin = new GetPin(status, type, ivrPhone);

        JsonObject setPinJson = jObject.get("set_pin").getAsJsonObject();
        SetPin setPin = new SetPin(ParsingUtils.getStringFromJson(setPinJson.get("status")));

        JsonObject selectBalanceStoreJson = jObject.get("select_balance_store").getAsJsonObject();
        AllowedBalancesTypesList allowedBalancesTypesList = new Gson().fromJson(selectBalanceStoreJson.get("allowed_balance_types"), AllowedBalancesTypesList.class);
        ArrayList<AllowedBalanceType> allowedBalanceTypes = new ArrayList<>();
        if(allowedBalancesTypesList != null) {
            allowedBalanceTypes.addAll(Arrays.asList(allowedBalancesTypesList.data));
        }

        SelectBalanceStore selectBalanceStore = new SelectBalanceStore(allowedBalanceTypes);
        return new Features(getPin, setPin, selectBalanceStore);
    }
}
