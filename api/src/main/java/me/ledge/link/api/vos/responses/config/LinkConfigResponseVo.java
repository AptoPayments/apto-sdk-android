package me.ledge.link.api.vos.responses.config;

/**
 * Link configuration data in response.
 * @author Adrian
 */

import com.google.gson.annotations.SerializedName;

public class LinkConfigResponseVo {

    @SerializedName("loan_amount_max")
    public int loanAmountMax;

    @SerializedName("loan_amount_increments")
    public int loanAmountIncrements;

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
    public LinkDisclaimerVo linkDisclaimer;

    @SerializedName("loan_products")
    public LoanProductListVo loanProductList;
}