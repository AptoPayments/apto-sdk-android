package com.shiftpayments.link.sdk.ui.geocoding.vos;

/**
 * Google geocoding response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeocodingResultVo {

    @SerializedName("results")
    private List<ResultVo> results = null;

    @SerializedName("status")
    private String status;

    public List<ResultVo> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}