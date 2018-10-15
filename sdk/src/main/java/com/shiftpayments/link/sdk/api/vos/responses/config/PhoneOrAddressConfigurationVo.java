package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneOrAddressConfigurationVo extends DataPointConfigurationVo {
    @SerializedName("allowed_countries")
    public List<String> allowedCountries;

    @SerializedName("sync_country")
    public Boolean syncCountry;

    public PhoneOrAddressConfigurationVo(String type, List<String> allowedCountries, Boolean syncCountry) {
        super(type);
        this.allowedCountries = allowedCountries;
        this.syncCountry = syncCountry;
    }
}
