package com.shift.link.sdk.api.vos.responses.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 20/03/2018.
 */

public class TransactionVo {

    public String id;

    @SerializedName("authorized")
    public boolean isAuthorized;

    @SerializedName("created_at")
    public String creationTime;

    public String description;

    @SerializedName("merchant_name")
    public String merchantName;

    @SerializedName("merchant_city")
    public String merchantCity;

    @SerializedName("merchant_state")
    public String merchantState;

    @SerializedName("merchant_country")
    public String merchantCountry;

    @SerializedName("mcc_code")
    public int merchantCategoryCode;

    @SerializedName("mcc_name")
    public String merchantCategoryName;

    @SerializedName("mcc_icon")
    public String merchantCategoryIcon;

    public String state;

    @SerializedName("local_amount")
    public double localAmount;

    @SerializedName("local_currency")
    public String localCurrency;

    @SerializedName("usd_amount")
    public double usdAmount;

    @SerializedName("cashback_amount")
    public double cashBackAmount;

    @SerializedName("ecommerce")
    public boolean isECommerce;

    @SerializedName("international")
    public boolean isInternational;

    @SerializedName("card_present")
    public boolean isCardPresent;

    @SerializedName("emv")
    public boolean isEMV;

    @SerializedName("network")
    public String netowrk;
}
