package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

public class ProjectBrandingVo {
    @SerializedName("ui_primary_color")
    public String uiPrimaryColor;

    @SerializedName("ui_secondary_color")
    public String uiSecondaryColor;

    @SerializedName("ui_tertiary_color")
    public String uiTertiaryColor;

    @SerializedName("ui_error_color")
    public String uiErrorColor;

    @SerializedName("ui_success_color")
    public String uiSuccessColor;

    @SerializedName("text_primary_color")
    public String textPrimaryColor;

    @SerializedName("text_secondary_color")
    public String textSecondaryColor;

    @SerializedName("text_tertiary_color")
    public String textTertiaryColor;

    @SerializedName("text_link_color")
    public String textLinkColor;

    @SerializedName("text_topbar_color")
    public String textTopbarColor;

    @SerializedName("icon_primary_color")
    public String iconPrimaryColor;

    @SerializedName("icon_secondary_color")
    public String iconSecondaryColor;

    @SerializedName("icon_tertiary_color")
    public String iconTertiaryColor;

    @SerializedName("logo_url")
    public String logoURL;

    @SerializedName("card_background_color")
    public String cardBackgroundColor;
}
