package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.shiftpayments.link.sdk.api.vos.Card;
import com.shiftpayments.link.sdk.api.vos.datapoints.Address;
import com.shiftpayments.link.sdk.api.vos.datapoints.ArmedForces;
import com.shiftpayments.link.sdk.api.vos.datapoints.BankAccount;
import com.shiftpayments.link.sdk.api.vos.datapoints.Birthdate;
import com.shiftpayments.link.sdk.api.vos.datapoints.CreditScore;
import com.shiftpayments.link.sdk.api.vos.datapoints.Custodian;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.Email;
import com.shiftpayments.link.sdk.api.vos.datapoints.Housing;
import com.shiftpayments.link.sdk.api.vos.datapoints.Income;
import com.shiftpayments.link.sdk.api.vos.datapoints.IncomeSource;
import com.shiftpayments.link.sdk.api.vos.datapoints.PaydayLoan;
import com.shiftpayments.link.sdk.api.vos.datapoints.PersonalName;
import com.shiftpayments.link.sdk.api.vos.datapoints.PhoneNumberVo;
import com.shiftpayments.link.sdk.api.vos.datapoints.SSN;
import com.shiftpayments.link.sdk.api.vos.datapoints.TimeAtAddress;
import com.shiftpayments.link.sdk.api.vos.Card;

import java.lang.reflect.Type;

/**
 * Created by adrian on 25/01/2017.
 */

public class DataPointParser implements JsonDeserializer<DataPointVo>, JsonSerializer<DataPointVo>{
    @Override
    public DataPointVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String type = jObject.get("type").getAsString();
        boolean verified = false;
        if(jObject.has("verified")) {
            verified = jObject.get("verified").getAsBoolean();
        }
        boolean notSpecified = false;
        if(jObject.has("not_specified")) {
            notSpecified = jObject.get("not_specified").getAsBoolean();
        }
        switch (type) {
            case "name":
                return new PersonalName(jObject.get("first_name").getAsString(),
                        jObject.get("last_name").getAsString(), verified, notSpecified);
            case "phone":
                return new PhoneNumberVo(jObject.get("country_code").getAsString() +
                        jObject.get("phone_number").getAsString(), verified, notSpecified);
            case "email":
                if(notSpecified) {
                    return new Email(null, verified, notSpecified);
                }
                return new Email(jObject.get("email").getAsString(), verified, notSpecified);
            case "birthdate":
                return new Birthdate(jObject.get("date").getAsString(), verified, notSpecified);
            case "ssn":
                // Temporary patch until the server doesn't return the SSN datapoint
                return new SSN();
            case "address":
                return new Address(jObject.get("address").getAsString(),
                        jObject.get("apt").getAsString(), "US", jObject.get("city").getAsString(),
                        jObject.get("state").getAsString(), jObject.get("zip").getAsString(),
                        verified, notSpecified);
            case "housing":
                return new Housing(jObject.get("housing_type_id").getAsInt(), verified,
                        notSpecified);
            case "income_source":
                return new IncomeSource(jObject.get("income_type_id").getAsInt(),
                        jObject.get("salary_frequency_id").getAsInt(), verified, notSpecified);
            case "income":
                return new Income(jObject.get("net_monthly_income").getAsDouble(),
                        jObject.get("gross_annual_income").getAsLong(), verified, notSpecified);
            case "credit_score":
                return new CreditScore(jObject.get("credit_range").getAsInt(), verified,
                        notSpecified);
            case "card":
                String cardState = ParsingUtils.getStringFromJson(jObject.get("state")) == null ? ""
                        : ParsingUtils.getStringFromJson(jObject.get("state")).toUpperCase();
                return new Card(jObject.get("account_id").getAsString(),
                        ParsingUtils.getStringFromJson(jObject.get("last_four")),
                        Card.CardNetwork.valueOf(ParsingUtils.getStringFromJson(jObject.get("card_network"))),
                        ParsingUtils.getStringFromJson(jObject.get("card_brand")),
                        ParsingUtils.getStringFromJson(jObject.get("card_issuer")),
                        ParsingUtils.getStringFromJson(jObject.get("expiration")),
                        ParsingUtils.getStringFromJson(jObject.get("pan")),
                        ParsingUtils.getStringFromJson(jObject.get("cvv")),
                        Card.FinancialAccountState.valueOf(cardState),
                        new Custodian("coinbase", "logo", "coinbase", ""),
                        false);
            case "bank_account":
                return new BankAccount(jObject.get("account_id").getAsString(),
                        jObject.get("bank_name").getAsString(),
                        jObject.get("last_four").getAsString(), false);
            case "payday_loan":
                return new PaydayLoan(jObject.get("payday_loan").getAsBoolean(), verified,
                        notSpecified);
            case "member_of_armed_forces":
                return new ArmedForces(jObject.get("member_of_armed_forces").getAsBoolean(),
                        verified, notSpecified);
            case "time_at_address":
                return new TimeAtAddress(jObject.get("time_at_address_id").getAsInt(), verified,
                        notSpecified);
        }
        return null;
    }

    @Override
    public JsonElement serialize(DataPointVo src, Type typeOfSrc, JsonSerializationContext context) {
        // Cast to Object required due to Gson library limitations: https://stackoverflow.com/a/15016251
        return context.serialize( (Object) src.toJSON());
    }
}
