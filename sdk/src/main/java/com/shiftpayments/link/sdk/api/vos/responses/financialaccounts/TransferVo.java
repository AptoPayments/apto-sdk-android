package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TransferVo implements Parcelable {

    public static final Creator<TransferVo> CREATOR = new Creator<TransferVo>() {
        @Override
        public TransferVo createFromParcel(Parcel in) {
            return new TransferVo(in);
        }

        @Override
        public TransferVo[] newArray(int size) {
            return new TransferVo[size];
        }
    };

    public TransactionVo.TransactionType type;
    @SerializedName("user_balance_name")
    public String userBalanceName;
    @SerializedName("funding_source_transaction_id")
    public String fundingSourceTransactionId;
    @SerializedName("usd_exchange_rate")
    public String usdExchangeRate;
    @SerializedName("usd_amt")
    public Double usdAmount;

    public TransferVo(Parcel in) {
        fundingSourceTransactionId = in.readString();
        type = TransactionVo.TransactionType.valueOf(in.readString());
        userBalanceName = in.readString();
        usdExchangeRate = in.readString();
        usdAmount = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fundingSourceTransactionId);
        parcel.writeString(type.name());
        parcel.writeString(userBalanceName);
        parcel.writeString(usdExchangeRate);
        parcel.writeDouble(usdAmount);
    }
}