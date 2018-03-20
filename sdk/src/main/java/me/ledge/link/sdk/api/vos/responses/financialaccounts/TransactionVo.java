package me.ledge.link.sdk.api.vos.responses.financialaccounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 20/03/2018.
 */

public class TransactionVo {

    public int id;

    @SerializedName("authorized")
    public boolean isAuthorized;

    @SerializedName("created_at")
    public String creationTime;

    public String description;

    @SerializedName("merchantName")
    public String merchantName;

    @SerializedName("merchantCity")
    public String merchantCity;

    @SerializedName("merchantState")
    public String merchantState;

    @SerializedName("merchantCountry")
    public String merchantCountry;

    @SerializedName("mcc_code")
    public int merchantCategoryCode;

    @SerializedName("mcc_name")
    public String merchantCategoryName;

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
