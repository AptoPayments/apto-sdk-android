package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

import com.google.gson.annotations.SerializedName;

public class CardProductVo {

    @SerializedName("type")
    public String type;
    @SerializedName("team_id")
    public String teamId;
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("summary")
    public Object summary;
    @SerializedName("website")
    public Object website;
    @SerializedName("status")
    public String status;
    @SerializedName("shared")
    public Boolean shared;
    @SerializedName("disclaimer_action")
    public DisclaimerAction disclaimerAction;
    @SerializedName("card_issuer")
    public String cardIssuer;
    @SerializedName("external_id")
    public String externalId;
    @SerializedName("external_username")
    public String externalUsername;
    @SerializedName("external_password")
    public String externalPassword;
}
