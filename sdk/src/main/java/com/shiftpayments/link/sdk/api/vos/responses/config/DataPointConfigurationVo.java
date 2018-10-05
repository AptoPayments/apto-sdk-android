package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataPointConfigurationVo {
    @SerializedName("type")
    public String type;

    @SerializedName("allowed_countries")
    public List<String> allowedCountries;

    @SerializedName("sync_country")
    public Boolean syncCountry;

    public DataPointConfigurationVo(String type, List<String> allowedCountries, Boolean syncCountry) {
        this.type = type;
        this.allowedCountries = allowedCountries;
        this.syncCountry = syncCountry;
    }
}
