package me.ledge.link.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

/**
 * Loan product data.
 * @author Adrian
 */
public class LoanProductVo {

    public int status;

    @SerializedName("pre_qualification_disclaimer")
    public DisclaimerVo preQualificationDisclaimer;

    public String id;

    @SerializedName("application_disclaimer")
    public DisclaimerVo applicationDisclaimer;

    @SerializedName("esign_disclaimer")
    public DisclaimerVo eSignDisclaimer;

    @SerializedName("esign_consent_disclaimer")
    public DisclaimerVo eSignConsentDisclaimer;

    public String type;

    @SerializedName("product_name")
    public String productName;
}
