package me.ledge.link.api.vos.responses.config;

/**
 * Configuration data in response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class ConfigResponseVo {

    public String name;

    @SerializedName("primary_color")
    public String primaryColor;

    @SerializedName("secondary_color")
    public String secondaryColor;

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

    @SerializedName("employment_statuses")
    public EmploymentStatusListResponseVo employmentStatusOpts;

    @SerializedName("salary_frequencies")
    public SalaryFrequenciesListResponseVo salaryFrequencyOpts;

    @SerializedName("time_at_address_values")
    public TimeAtAddressListResponseVo timeAtAddressOpts;

    public ProductsListResponseVo products;
}