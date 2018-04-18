package com.shift.link.sdk.api.vos.datapoints;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rits.cloning.Cloner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataPointList {
    public enum ListType {
        userData,
        financialAccounts
    }
    private HashMap<DataPointVo.DataPointType, List<DataPointVo>> dataPoints;
    private ListType type;

    public DataPointList() {
        dataPoints = new HashMap<>();
    }

    public DataPointList(ListType type) {
        dataPoints = new HashMap<>();
        this.type = type;
    }

    public DataPointList(DataPointList copyInstance) {
        if(copyInstance == null) {
            return;
        }
        Cloner cloner = new Cloner();
        this.dataPoints = cloner.deepClone(copyInstance.dataPoints);
    }

    public void init() {
        dataPoints = null;
    }

    public void add(DataPointVo dataPoint) {
        if(dataPoints.containsKey(dataPoint.getType())) {
            List<DataPointVo> dataPointList = this.getDataPointsOf(dataPoint.getType());
            if(!dataPointList.contains(dataPoint)) {
                dataPointList.add(dataPoint);
            }
            dataPoints.put(dataPoint.getType(), dataPointList);
        }
        else {
            dataPoints.put(dataPoint.getType(), new LinkedList<>(Arrays.asList(dataPoint)));
        }
    }

    public void removeDataPointsOf(DataPointVo.DataPointType type) {
        if(dataPoints.containsKey(type)) {
            dataPoints.remove(type);
        }
    }

    /**
     * @return Single DataPoint of requested type or the default value if not found
     */
    public DataPointVo getUniqueDataPoint(DataPointVo.DataPointType key, DataPointVo defaultValue) {
        if(dataPoints.containsKey(key)) {
            return dataPoints.get(key).get(0);
        }
        else {
            if(defaultValue!=null) {
                dataPoints.put(key, new LinkedList<>(Arrays.asList(defaultValue)));
            }
            return defaultValue;
        }
    }

    public List<DataPointVo> getDataPointsOf(DataPointVo.DataPointType key) {
        if(dataPoints.containsKey(key)) {
            return dataPoints.get(key);
        }
        else {
            return null;
        }
    }

    public void setDataPoints(HashMap<DataPointVo.DataPointType, List<DataPointVo>> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public HashMap<DataPointVo.DataPointType, List<DataPointVo>> getDataPoints() {
        return dataPoints;
    }

    public JsonObject toJSON() {
        JsonObject gsonObject = new JsonObject();
        JsonArray gsonArray = new JsonArray();
        for (List<DataPointVo> dataPoints : this.getDataPoints().values()) {
            for(DataPointVo dataPointVo : dataPoints) {
                gsonArray.add(dataPointVo.toJSON());
            }
        }
        gsonObject.add("data", gsonArray);
        gsonObject.addProperty("type", "list");
        JsonObject result = new JsonObject();
        result.add("data_points", gsonObject);
        return result;
    }

    public void setType(ListType type) {
        this.type = type;
    }

    public ListType getType() {
        return this.type;
    }
}