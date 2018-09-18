package com.shiftpayments.link.sdk.api.vos.responses.cardconfig;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.config.ContentVo;

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
    @SerializedName("card_issuer")
    public String cardIssuer;
    @SerializedName("external_id")
    public String externalId;
    @SerializedName("external_username")
    public String externalUsername;
    @SerializedName("external_password")
    public String externalPassword;
    @SerializedName("cardholder_agreement")
    public ContentVo cardholderAgreement;
    @SerializedName("privacy_policy")
    public ContentVo privacyPolicy;
    @SerializedName("terms_of_service")
    public ContentVo termsOfService;
    @SerializedName("faq")
    public ContentVo faq;
}
