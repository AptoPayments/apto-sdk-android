package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adrian on 04/04/2018.
 */

public class MoneyVo implements Parcelable {

    public String type;

    public String currency;

    public Double amount;

    protected MoneyVo(Parcel in) {
        type = in.readString();
        currency = in.readString();
        amount = (Double) in.readValue(Double.class.getClassLoader());
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
        parcel.writeValue(amount);
    }

    public boolean hasAmount() {
        return amount != null;
    }
}
