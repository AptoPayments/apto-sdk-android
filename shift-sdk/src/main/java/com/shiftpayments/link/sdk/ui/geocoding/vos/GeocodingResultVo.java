package com.shiftpayments.link.sdk.ui.geocoding.vos;

/**
 * Google geocoding response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class GeocodingResultVo {

    @SerializedName("result")
    public ResultVo result;

    @SerializedName("status")
    public String status;
}