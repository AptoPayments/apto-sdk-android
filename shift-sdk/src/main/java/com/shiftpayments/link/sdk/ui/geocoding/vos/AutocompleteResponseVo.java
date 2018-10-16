package com.shiftpayments.link.sdk.ui.geocoding.vos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutocompleteResponseVo {
    @SerializedName("status")
    public String status;

    @SerializedName("predictions")
    public List<PredictionVo> predictions;
}
