package me.ledge.link.api.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.responses.config.RequiredDataPointVo;

/**
 * Created by adrian on 25/01/2017.
 */

public class RequiredDataPointParser implements JsonDeserializer<RequiredDataPointVo> {
    @Override
    public RequiredDataPointVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jObject = json.getAsJsonObject();
        String type = jObject.get("datapoint_type").getAsString();
        Boolean isVerificationRequired = jObject.get("verification_required").getAsBoolean();
        switch (type) {
            case "name":
                return new RequiredDataPointVo(DataPointVo.DataPointType.PersonalName,
                        isVerificationRequired);
            case "phone":
                return new RequiredDataPointVo(DataPointVo.DataPointType.PhoneNumber,
                        isVerificationRequired);
            case "email":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Email,
                        isVerificationRequired);
            case "birthdate":
                return new RequiredDataPointVo(DataPointVo.DataPointType.BirthDate,
                        isVerificationRequired);
            case "ssn":
                return new RequiredDataPointVo(DataPointVo.DataPointType.SSN,
                        isVerificationRequired);
            case "address":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Address,
                        isVerificationRequired);
            case "housing":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Housing,
                        isVerificationRequired);
            case "employment":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Employment,
                        isVerificationRequired);
            case "income":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Income,
                        isVerificationRequired);
            case "credit_score":
                return new RequiredDataPointVo(DataPointVo.DataPointType.CreditScore,
                        isVerificationRequired);
        }
        return null;
    }
}
