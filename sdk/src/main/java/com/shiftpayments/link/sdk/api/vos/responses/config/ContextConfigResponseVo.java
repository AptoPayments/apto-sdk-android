package com.shiftpayments.link.sdk.api.vos.responses.config;

/**
 * Configuration data in response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class ContextConfigResponseVo {

    public String name;

    @SerializedName("project")
    public ConfigResponseVo projectConfiguration;

    @SerializedName("team")
    public TeamConfigResponseVo teamConfiguration;
}