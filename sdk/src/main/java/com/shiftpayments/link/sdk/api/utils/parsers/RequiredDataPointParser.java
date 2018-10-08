package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.DataPointConfigurationVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;

import java.lang.reflect.Type;
import java.util.Arrays;

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
        Boolean isNotSpecifiedAllowed = jObject.get("not_specified_allowed").getAsBoolean();
        switch (type) {
            case "name":
                return new RequiredDataPointVo(DataPointVo.DataPointType.PersonalName,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "phone":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Phone,
                        isVerificationRequired, isNotSpecifiedAllowed,
                        parseDataPointConfiguration(jObject));
            case "email":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Email,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "birthdate":
                return new RequiredDataPointVo(DataPointVo.DataPointType.BirthDate,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "id_document":
                return new RequiredDataPointVo(DataPointVo.DataPointType.IdDocument,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "address":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Address,
                        isVerificationRequired, isNotSpecifiedAllowed,
                        parseDataPointConfiguration(jObject));
            case "housing":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Housing,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "income_source":
                return new RequiredDataPointVo(DataPointVo.DataPointType.IncomeSource,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "income":
                return new RequiredDataPointVo(DataPointVo.DataPointType.Income,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "credit_score":
                return new RequiredDataPointVo(DataPointVo.DataPointType.CreditScore,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "payday_loan":
                return new RequiredDataPointVo(DataPointVo.DataPointType.PayDayLoan,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "member_of_armed_forces":
                return new RequiredDataPointVo(DataPointVo.DataPointType.MemberOfArmedForces,
                        isVerificationRequired, isNotSpecifiedAllowed);
            case "time_at_address":
                return new RequiredDataPointVo(DataPointVo.DataPointType.TimeAtAddress,
                        isVerificationRequired, isNotSpecifiedAllowed);
        }
        return null;
    }

    private DataPointConfigurationVo parseDataPointConfiguration(JsonObject jObject) {
        if(!jObject.has("datapoint_configuration") || jObject.get("datapoint_configuration").isJsonNull()) {
            return null;
        }
        JsonObject config = jObject.getAsJsonObject("datapoint_configuration");
        String type = config.get("type").getAsString();
        String[] allowedCountries = new Gson().fromJson(config.get("allowed_countries"), String[].class);
        Boolean syncCountry = null;
        if(config.has("sync_country")) {
            syncCountry = config.get("sync_country").getAsBoolean();
        }

        return new DataPointConfigurationVo(type, Arrays.asList(allowedCountries), syncCountry);
    }
}
