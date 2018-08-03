package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

/**
 * Info about the DataPoint group.
 * @author Adrian
 */
public class DataPointGroupVo {

    @SerializedName("type")
    public String type;

    @SerializedName("datapoint_group_id")
    public String datapointGroupId;

    @SerializedName("datapoint_group_type")
    public String datapointGroupType;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public Object description;

    @SerializedName("order")
    public Integer order;

    @SerializedName("datapoints")
    public RequiredDataPointsListResponseVo datapoints;

    /**
     * No args constructor for use in serialization
     *
     */
    public DataPointGroupVo() {
    }

    public DataPointGroupVo(String type, String datapointGroupId, String datapointGroupType, String name, Object description, Integer order, RequiredDataPointsListResponseVo datapoints) {
        super();
        this.type = type;
        this.datapointGroupId = datapointGroupId;
        this.datapointGroupType = datapointGroupType;
        this.name = name;
        this.description = description;
        this.order = order;
        this.datapoints = datapoints;
    }
}
