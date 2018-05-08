package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adrian on 04/04/2018.
 */

public class MoneyVo implements Parcelable {

    public String type;

    public String currency;

    public double amount;

    protected MoneyVo(Parcel in) {
        type = in.readString();
        currency = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<MoneyVo> CREATOR = new Creator<MoneyVo>() {
        @Override
        public MoneyVo createFromParcel(Parcel in) {
            return new MoneyVo(in);
        }

        @Override
        public MoneyVo[] newArray(int size) {
            return new MoneyVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(currency);
        parcel.writeDouble(amount);
    }
}
