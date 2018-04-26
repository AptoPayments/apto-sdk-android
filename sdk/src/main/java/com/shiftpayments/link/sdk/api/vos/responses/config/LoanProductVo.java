package com.shiftpayments.link.sdk.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

/**
 * Loan product data.
 * @author Adrian
 */
public class LoanProductVo {

    public int status;

    @SerializedName("pre_qualification_disclaimer")
    public ContentVo preQualificationDisclaimer;

    public String id;

    @SerializedName("application_disclaimer")
    public ContentVo applicationDisclaimer;

    @SerializedName("esign_disclaimer")
    public ContentVo eSignDisclaimer;

    @SerializedName("esign_consent_disclaimer")
    public ContentVo eSignConsentDisclaimer;

    public String type;

    @SerializedName("product_name")
    public String productName;
}
