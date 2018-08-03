package com.shiftpayments.link.sdk.api.vos.responses.config;

/*
 * Link configuration data in response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class LinkConfigResponseVo {

    @SerializedName("loan_amount_min")
    public double loanAmountMin;

    @SerializedName("loan_amount_max")
    public double loanAmountMax;

    @SerializedName("loan_amount_increments")
    public double loanAmountIncrements;

    @SerializedName("loan_amount_default")
    public double loanAmountDefault;

    @SerializedName("pos_mode")
    public boolean posMode;

    @SerializedName("skip_link_disclaimer")
    public boolean skipLinkDisclaimer;

    @SerializedName("offer_list_style")
    public String offerListStyle;

    @SerializedName("loan_purposes")
    public LoanPurposesResponseVo loanPurposesList;

    @SerializedName("user_required_data")
    public RequiredDataPointsListResponseVo userRequiredData;

    @SerializedName("link_disclaimer")
    public ContentVo linkDisclaimer;

    @SerializedName("loan_products")
    public LoanProductListVo loanProductList;

    @SerializedName("strict_address_validation")
    public boolean isStrictAddressValidationEnabled;

    @SerializedName("skip_loan_amount")
    public boolean skipLoanAmount;

    @SerializedName("skip_loan_purpose")
    public boolean skipLoanPurpose;
}