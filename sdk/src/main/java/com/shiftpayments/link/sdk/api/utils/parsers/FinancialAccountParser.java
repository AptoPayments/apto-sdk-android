package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.BankAccount;
import com.shiftpayments.link.sdk.api.vos.datapoints.FinancialAccountVo;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.KycStatus;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.MoneyVo;
import com.shiftpayments.link.sdk.api.vos.responses.card.Features;

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
                    jObject.get("features").getAsJsonObject(), iType, context);

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
                    parseAmount(jObject.get("spendable_today").getAsJsonObject()),
                    parseAmount(jObject.get("native_spendable_today").getAsJsonObject()),
                    jObject.get("physical_card_activation_required").getAsBoolean(),
                    features,
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

    private MoneyVo parseAmount(JsonObject jObject ) {
        Double amount = jObject.get("amount").getAsDouble();
        String currency = ParsingUtils.getStringFromJson(jObject.get("currency"));
        return new MoneyVo(amount, currency);
    }
}
