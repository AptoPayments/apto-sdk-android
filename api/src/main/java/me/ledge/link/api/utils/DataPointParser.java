package me.ledge.link.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import me.ledge.link.api.vos.datapoints.Address;
import me.ledge.link.api.vos.datapoints.BankAccount;
import me.ledge.link.api.vos.datapoints.Birthdate;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.datapoints.CreditScore;
import me.ledge.link.api.vos.datapoints.Email;
import me.ledge.link.api.vos.datapoints.Employment;
import me.ledge.link.api.vos.datapoints.Housing;
import me.ledge.link.api.vos.datapoints.Income;
import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.datapoints.PersonalName;
import me.ledge.link.api.vos.datapoints.PhoneNumberVo;
import me.ledge.link.api.vos.datapoints.SSN;

/**
 * Created by adrian on 25/01/2017.
 */

public class DataPointParser implements JsonDeserializer<DataPointVo> {
    @Override
    public DataPointVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String type = jObject.get("type").getAsString();
        switch (type) {
            case "name":
                return new PersonalName(jObject.get("first_name").getAsString(),
                        jObject.get("last_name").getAsString(),
                        jObject.get("verified").getAsBoolean());
            case "phone":
                return new PhoneNumberVo(jObject.get("country_code").getAsString() +
                        jObject.get("phone_number").getAsString(),
                        jObject.get("verified").getAsBoolean());
            case "email":
                return new Email(jObject.get("email").getAsString(),
                        jObject.get("verified").getAsBoolean());
            case "birthdate":
                return new Birthdate(jObject.get("date").getAsString(),
                        jObject.get("verified").getAsBoolean());
            case "ssn":
                return new SSN();
            case "address":
                return new Address(jObject.get("address").getAsString(),
                        jObject.get("apt").getAsString(), "US", jObject.get("city").getAsString(),
                        jObject.get("state").getAsString(), jObject.get("zip").getAsString(),
                        jObject.get("verified").getAsBoolean());
            case "housing":
                return new Housing(jObject.get("housing_type_id").getAsInt(),
                        jObject.get("verified").getAsBoolean());
            case "employment":
                return new Employment(jObject.get("employment_status_id").getAsInt(),
                        jObject.get("salary_frequency_id").getAsInt(),
                        jObject.get("verified").getAsBoolean());
            case "income":
                return new Income(jObject.get("net_monthly_income").getAsDouble(),
                        jObject.get("gross_annual_income").getAsLong(),
                        jObject.get("verified").getAsBoolean());
            case "credit_score":
                return new CreditScore(jObject.get("credit_range").getAsInt(),
                        jObject.get("verified").getAsBoolean());
            case "card":
                return new Card(jObject.get("account_id").getAsString(),
                        Card.CardType.valueOf(jObject.get("card_type").getAsString()), null, null,
                        jObject.get("last_four_digits").getAsString(),
                        jObject.get("expiration").getAsString(), false);
            case "bank":
                return new BankAccount(jObject.get("account_id").getAsString(),
                        jObject.get("bank_name").getAsString(),
                        jObject.get("last_four_digits").getAsString(), false);
        }
        return null;
    }
}
