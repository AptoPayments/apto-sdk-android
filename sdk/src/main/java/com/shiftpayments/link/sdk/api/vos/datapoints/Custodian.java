package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pauteruel on 12/03/2018.
 */

public class Custodian {

    @SerializedName("name")
    public String name;
    public String logo;
    @SerializedName("custodian_type")
    public String type;
    public String id;

    public Custodian(String name, String logo, String type, String id) {
        this.name = name;
        this.logo = logo;
        this.type = type;
        this.id = id;
    }
}