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

    @SerializedName("housing_types")
    public HousingTypeListResponseVo housingTypeOpts;

    @SerializedName("employment_statuses")
    public EmploymentStatusListResponseVo employmentStatusOpts;

    @SerializedName("salary_frequencies")
    public SalaryFrequenciesListResponseVo salaryFrequencyOpts;

    public ProductsListResponseVo products;
}