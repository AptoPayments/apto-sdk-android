package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adrian on 20/03/2018.
 */

public class TransactionVo implements Parcelable {
    public enum TransactionType {
        SENT,
        DEPOSIT,
        ATM_WITHDRAWAL,
        WITHDRAWAL,
        SETTLEMENT,
        PIN_PURCHASE,
        FEE,
        REFUND,
        PENDING,
        BALANCE_INQUIRY,
        DECLINE,
        RECEIVED,
        REVERSAL,
        UNSUPPORTED,
        UNAVAILABLE
    }

    public TransactionType type;

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
    public boolean isEmv;

    @SerializedName("network")
    public String network;

    @SerializedName("decline_reason")
    public String declineReason;

    @SerializedName("hold_amount")
    public double holdAmount;

    @SerializedName("exchange_rate")
    public double exchangeRate;

    @SerializedName("settlement_date")
    public String settlementDate;

    public TransactionVo(Parcel in) {
        id = in.readString();
        isAuthorized = in.readInt() == 1;
        creationTime = in.readString();
        description = in.readString();
        merchantName = in.readString();
        merchantCity = in.readString();
        merchantState = in.readString();
        merchantCountry = in.readString();
        merchantCategoryCode = in.readInt();
        merchantCategoryName = in.readString();
        merchantCategoryIcon = in.readString();
        state = in.readString();
        localAmount = in.readDouble();
        localCurrency = in.readString();
        usdAmount = in.readDouble();
        cashBackAmount = in.readDouble();
        isECommerce = in.readInt() == 1;
        isInternational = in.readInt() == 1;
        isCardPresent = in.readInt() == 1;
        isEmv = in.readInt() == 1;
        network = in.readString();
        declineReason = in.readString();
        holdAmount = in.readDouble();
        exchangeRate = in.readDouble();
        settlementDate = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(id);
        parcel.writeInt(isAuthorized ? 1 : 0);
        parcel.writeString(creationTime);
        parcel.writeString(description);
        parcel.writeString(merchantName);
        parcel.writeString(merchantCity);
        parcel.writeString(merchantState);
        parcel.writeString(merchantCountry);
        parcel.writeInt(merchantCategoryCode);
        parcel.writeString(merchantCategoryName);
        parcel.writeString(merchantCategoryIcon);
        parcel.writeString(state);
        parcel.writeDouble(localAmount);
        parcel.writeString(localCurrency);
        parcel.writeDouble(usdAmount);
        parcel.writeDouble(cashBackAmount);
        parcel.writeInt(isECommerce ? 1 : 0);
        parcel.writeInt(isInternational ? 1 : 0);
        parcel.writeInt(isCardPresent ? 1 : 0);
        parcel.writeInt(isEmv ? 1 : 0);
        parcel.writeString(network);
        parcel.writeString(declineReason);
        parcel.writeDouble(holdAmount);
        parcel.writeDouble(exchangeRate);
        parcel.writeString(settlementDate);
    }

    public static final Parcelable.Creator<TransactionVo> CREATOR = new Parcelable.Creator<TransactionVo>() {
        public TransactionVo createFromParcel(Parcel in) {
            return new TransactionVo(in);
        }

        public TransactionVo[] newArray(int size) {
            return new TransactionVo[size];
        }
    };
}
