package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

/**
 * Team data.
 * @author Adrian
 */
public class TeamVo {

    public String type;

    public String name;

    @SerializedName("logo_url")
    public String logoURL;
}
