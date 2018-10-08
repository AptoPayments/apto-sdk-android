package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.IdDocumentConfigurationVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.PhoneOrAddressConfigurationVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
                        isVerificationRequired, isNotSpecifiedAllowed,
                        parseIdDocumentConfiguration(jObject));
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

    private PhoneOrAddressConfigurationVo parseDataPointConfiguration(JsonObject jObject) {
        if(!jObject.has("datapoint_configuration") || jObject.get("datapoint_configuration").isJsonNull()) {
            return null;
        }
        JsonObject config = jObject.getAsJsonObject("datapoint_configuration");
        String type = config.get("type").getAsString();
        String[] allowedCountries = new Gson().fromJson(config.get("allowed_countries"), String[].class);
        List<String> allowedCountriesArray = Arrays.asList(allowedCountries);
        if(allowedCountriesArray.isEmpty()) {
            allowedCountriesArray.add("US");
        }
        Boolean syncCountry = null;
        if(config.has("sync_country")) {
            syncCountry = config.get("sync_country").getAsBoolean();
        }

        return new PhoneOrAddressConfigurationVo(type, allowedCountriesArray, syncCountry);
    }

    private IdDocumentConfigurationVo parseIdDocumentConfiguration(JsonObject jObject) {
        // TODO: hardcoding config until backend is ready
        /*if(!jObject.has("datapoint_configuration") || jObject.get("datapoint_configuration").isJsonNull()) {
            return null;
        }
        JsonObject config = jObject.getAsJsonObject("datapoint_configuration");
        String type = config.get("type").getAsString();
        Map<String, List<String>> allowedDocumentsMap = new Gson().fromJson(config.get("allowed_document_types"), new TypeToken<Map<String, List<String>>>(){}.getType());

        if(allowedDocumentsMap.isEmpty()) {
            ArrayList<String> documentTypes = new ArrayList<>();
            documentTypes.add("SSN");
            allowedDocumentsMap.put("US", documentTypes);
        }*/
        String type = "id_document_datapoint_configuration";
        HashMap<String, List<String>> allowedDocumentsMap = new HashMap<>();
        ArrayList<String> documentTypes = new ArrayList<>();
        documentTypes.add("SSN");
        allowedDocumentsMap.put("US", documentTypes);

        return new IdDocumentConfigurationVo(type, allowedDocumentsMap);
    }
}
