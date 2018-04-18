package com.shift.link.sdk.ui.geocoding.vos;

/**
 * Google geocoding response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressComponentVo {

    @SerializedName("long_name")
    private String longName;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("types")
    private List<String> types = null;

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

    public List<String> getTypes() {
        return types;
    }
}