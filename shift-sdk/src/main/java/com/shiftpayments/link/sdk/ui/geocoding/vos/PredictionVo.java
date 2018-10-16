package com.shiftpayments.link.sdk.ui.geocoding.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PredictionVo {

    @SerializedName("description")
    public String description;

    @SerializedName("id")
    public String id;

    @SerializedName("place_id")
    public String placeId;

    @SerializedName("reference")
    public String reference;

    @SerializedName("types")
    public List<String> types = null;

    @SerializedName("structured_formatting")
    public StructuredText structuredText;

}