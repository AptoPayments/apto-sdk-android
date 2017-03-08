package me.ledge.link.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

/**
 * Loan product data.
 * @author Adrian
 */
public class LoanProductVo {

    public int status;

    @SerializedName("pre_qualification_disclaimer")
    public String preQualificationDisclaimer;

    public int id;

    @SerializedName("application_disclaimer")
    public String applicationDisclaimer;

    @SerializedName("esign_disclaimer")
    public String eSignDisclaimer;

    @SerializedName("esign_consent_disclaimer")
    public String eSignConsentDisclaimer;

    public String type;

    @SerializedName("product_name")
    public String productName;
}
