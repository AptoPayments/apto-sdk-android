package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SettlementVo implements Parcelable {
    @SerializedName("date")
    public String date;

    @SerializedName("amount")
    public MoneyVo amount;

    protected SettlementVo(Parcel in) {
        date = in.readString();
        amount = in.readParcelable(MoneyVo.class.getClassLoader());
    }

    public static final Creator<SettlementVo> CREATOR = new Creator<SettlementVo>() {
        @Override
        public SettlementVo createFromParcel(Parcel in) {
            return new SettlementVo(in);
        }

        @Override
        public SettlementVo[] newArray(int size) {
            return new SettlementVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeParcelable(amount, i);
    }
}
