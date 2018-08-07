package com.shiftpayments.link.sdk.api.vos.responses.config;

/*
 * Configuration data in response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.responses.workflow.ActionVo;

public class ConfigResponseVo {

    public String name;

    @SerializedName("primary_color")
    public String primaryColor;

    @SerializedName("secondary_color")
    public String secondaryColor;

    @SerializedName("tertiary_color")
    public String tertiaryColor;

    @SerializedName("error_color")
    public String errorColor;

    @SerializedName("success_color")
    public String successColor;

    @SerializedName("text_primary_color")
    public String textPrimaryColor;

    @SerializedName("text_secondary_color")
    public String textSecondaryColor;

    @SerializedName("text_tertiary_color")
    public String textTertiaryColor;

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

    @SerializedName("gross_income_min")
    public double grossIncomeMin;

    @SerializedName("gross_income_max")
    public double grossIncomeMax;

    @SerializedName("gross_income_increments")
    public double grossIncomeIncrements;

    @SerializedName("gross_income_default")
    public double grossIncomeDefault;

    @SerializedName("housing_types")
    public HousingTypeListResponseVo housingTypeOpts;

    @SerializedName("income_types")
    public IncomeTypeListResponseVo incomeTypeOpts;

    @SerializedName("salary_frequencies")
    public SalaryFrequenciesListResponseVo salaryFrequencyOpts;

    @SerializedName("time_at_address_values")
    public TimeAtAddressListResponseVo timeAtAddressOpts;

    @SerializedName("credit_score_values")
    public CreditScoreListResponseVo creditScoreOpts;

    public ProductsListResponseVo products;

    public String summary;

    public String language;

    @SerializedName("support_source_address")
    public String supportEmailAddress;

    @SerializedName("primary_auth_credential")
    public String primaryAuthCredential;

    @SerializedName("secondary_auth_credential")
    public String secondaryAuthCredential;

    @SerializedName("welcome_screen_action")
    public ActionVo welcomeScreenAction;
}