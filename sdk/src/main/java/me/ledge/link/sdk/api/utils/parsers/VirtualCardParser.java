package me.ledge.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import me.ledge.link.sdk.api.vos.datapoints.Card;
import me.ledge.link.sdk.api.vos.datapoints.Custodian;
import me.ledge.link.sdk.api.vos.datapoints.VirtualCard;

/**
 * Created by adrian on 25/01/2017.
 */

public class VirtualCardParser implements JsonDeserializer<VirtualCard> {
    @Override
    public VirtualCard deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        return new VirtualCard(jObject.get("account_id").getAsString(),
                ParsingUtils.getStringFromJson(jObject.get("last_four")),
                Card.CardNetwork.valueOf(ParsingUtils.getStringFromJson(jObject.get("card_network"))),
                ParsingUtils.getStringFromJson(jObject.get("card_brand")),
                ParsingUtils.getStringFromJson(jObject.get("card_issuer")),
                ParsingUtils.getStringFromJson(jObject.get("expiration")),
                ParsingUtils.getStringFromJson(jObject.get("pan")),
                ParsingUtils.getStringFromJson(jObject.get("cvv")),
                Card.FinancialAccountState.valueOf(ParsingUtils.getStringFromJson(jObject.get("state")).toUpperCase()),
                ParsingUtils.getStringFromJson(jObject.get("balance")),
                new Custodian("coinbase", "logo"),
                false);
    }
}
