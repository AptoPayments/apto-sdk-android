package com.shiftpayments.link.sdk.ui.geocoding.vos;

/**
 * Google geocoding response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultVo {

    @SerializedName("address_components")
    private List<AddressComponentVo> addressComponents = null;

    @SerializedName("formatted_address")
    private String formattedAddress;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("types")
    private List<String> types = null;

    @SerializedName("partial_match")
    public boolean isPartialMatch;

    public List<AddressComponentVo> getAddressComponents() {
        return addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public String getPlaceId() {
        return placeId;
    }

    public List<String> getTypes() {
        return types;
    }
}