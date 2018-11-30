package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.BankAccount;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.responses.card.CardBackground;
import com.shiftpayments.link.sdk.api.vos.responses.card.CardBackgroundColor;
import com.shiftpayments.link.sdk.api.vos.responses.card.CardBackgroundImage;
import com.shiftpayments.link.sdk.api.vos.responses.card.CardStyle;
import com.shiftpayments.link.sdk.api.vos.responses.card.Features;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.MoneyVo;

import java.lang.reflect.Type;

/**
 * Created by adrian on 25/01/2017.
 */

public class FinancialAccountParser implements JsonDeserializer<FinancialAccountVo> {
    @Override
    public FinancialAccountVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String type = ParsingUtils.getStringFromJson(jObject.get("type"));
        if(type == null) {
            return null;
        }
        if(type.equalsIgnoreCase("card")) {
            String cardState = ParsingUtils.getStringFromJson(jObject.get("state")) == null ? ""
                    : ParsingUtils.getStringFromJson(jObject.get("state")).toUpperCase();
            String kycStatus = ParsingUtils.getStringFromJson(jObject.get("kyc_status")) == null ? ""
                    : ParsingUtils.getStringFromJson(jObject.get("kyc_status"));

            Features features = new FeaturesParser().deserialize(
                    jObject.get("features"), iType, context);

            Boolean physicalCardActivationRequired = jObject.has("physical_card_activation_required")
                    && !jObject.get("physical_card_activation_required").isJsonNull()
                    && jObject.get("physical_card_activation_required").getAsBoolean();
            Gson gson = new Gson();
            MoneyVo spendableToday = gson.fromJson(jObject.get("spendable_today"), MoneyVo.class);
            MoneyVo nativeSpendableToday = gson.fromJson(jObject.get("native_spendable_today"), MoneyVo.class);
            return new Card(jObject.get("account_id").getAsString(),
                    ParsingUtils.getStringFromJson(jObject.get("last_four")),
                    Card.CardNetwork.valueOf(ParsingUtils.getStringFromJson(jObject.get("card_network"))),
                    ParsingUtils.getStringFromJson(jObject.get("card_brand")),
                    ParsingUtils.getStringFromJson(jObject.get("card_issuer")),
                    ParsingUtils.getStringFromJson(jObject.get("expiration")),
                    ParsingUtils.getStringFromJson(jObject.get("pan")),
                    ParsingUtils.getStringFromJson(jObject.get("cvv")),
                    Card.FinancialAccountState.valueOf(cardState),
                    KycStatus.valueOf(kycStatus),
                    // TODO
                    null,
                    spendableToday,
                    nativeSpendableToday,
                    physicalCardActivationRequired,
                    features,
                    parseCardStyle(jObject.get("card_style")),
                    false);
        }
        else if(type.equalsIgnoreCase("bank_account")) {
            return new BankAccount(jObject.get("account_id").getAsString(),
                    jObject.get("bank_name").getAsString(),
                    jObject.get("last_four").getAsString(), false);
        }
        else {
            return null;
        }
    }

    private CardStyle parseCardStyle(JsonElement json) {
        if(ParsingUtils.getJsonObject(json) == null) {
            return null;
        }
        JsonObject jObject = json.getAsJsonObject();
        JsonObject backgroundJson = jObject.get("background").getAsJsonObject();
        String backgroundType = ParsingUtils.getStringFromJson(backgroundJson.get("background_type"));
        CardBackground background = null;
        switch(backgroundType) {
            case "color":
                background = new CardBackgroundColor(ParsingUtils.getStringFromJson(backgroundJson.get("background_color")));
                break;
            case "image":
                background = new CardBackgroundImage(ParsingUtils.getStringFromJson(backgroundJson.get("background_image")));
                break;
        }
        return new CardStyle(background);
    }
}
