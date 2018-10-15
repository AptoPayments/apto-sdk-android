package com.shiftpayments.link.sdk.api.utils.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.shiftpayments.link.sdk.api.vos.responses.config.DataPointGroupVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointVo;
import com.shiftpayments.link.sdk.api.vos.responses.config.RequiredDataPointsListResponseVo;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataPointGroupParser implements JsonDeserializer<DataPointGroupVo> {
    @Override
    public DataPointGroupVo deserialize(JsonElement json, Type iType, JsonDeserializationContext context) throws JsonParseException {
        JsonObject dataPointGroupJson = json.getAsJsonObject();
        String type = ParsingUtils.getStringFromJson(dataPointGroupJson.get("type"));
        String datapointGroupId = ParsingUtils.getStringFromJson(dataPointGroupJson.get("datapoint_group_id"));
        String datapointGroupType = ParsingUtils.getStringFromJson(dataPointGroupJson.get("datapoint_group_type"));
        String name = ParsingUtils.getStringFromJson(dataPointGroupJson.get("name"));
        String description = ParsingUtils.getStringFromJson(dataPointGroupJson.get("description"));
        int order = dataPointGroupJson.get("order").getAsInt();
        JsonArray requiredDataPointJsonArray = dataPointGroupJson.get("datapoints").getAsJsonObject().getAsJsonArray("data");
        RequiredDataPointsListResponseVo requiredDataPointsListResponse = deserializeRequiredDatapoints(requiredDataPointJsonArray, iType, context);
        return new DataPointGroupVo(type, datapointGroupId, datapointGroupType, name, description, order, requiredDataPointsListResponse);
    }

    private RequiredDataPointsListResponseVo deserializeRequiredDatapoints(JsonArray requiredDataPointJsonArray, Type iType, JsonDeserializationContext context) {
        RequiredDataPointParser requiredDataPointParser = new RequiredDataPointParser();
        ArrayList<RequiredDataPointVo> requiredDataPointsArrayList = new ArrayList<>();
        for (JsonElement jsonElement : requiredDataPointJsonArray) {
            RequiredDataPointVo requiredDataPointVo = requiredDataPointParser.deserialize(jsonElement, iType, context);
            requiredDataPointsArrayList.add(requiredDataPointVo);
        }
        RequiredDataPointVo[] requiredDataPoints = requiredDataPointsArrayList.toArray(new RequiredDataPointVo[0]);
        RequiredDataPointsListResponseVo requiredDataPointsListResponse = new RequiredDataPointsListResponseVo();
        requiredDataPointsListResponse.data = requiredDataPoints;
        requiredDataPointsListResponse.total_count = requiredDataPoints.length;
        return requiredDataPointsListResponse;
    }
}
