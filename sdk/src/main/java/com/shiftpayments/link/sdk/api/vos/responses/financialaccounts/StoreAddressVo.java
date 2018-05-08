package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreAddressVo implements Parcelable {
    public String city;
    public String state;
    public String country;

    protected StoreAddressVo(Parcel in) {
        city = in.readString();
        state = in.readString();
        country = in.readString();
    }

    public static final Creator<StoreAddressVo> CREATOR = new Creator<StoreAddressVo>() {
        @Override
        public StoreAddressVo createFromParcel(Parcel in) {
            return new StoreAddressVo(in);
        }

        @Override
        public StoreAddressVo[] newArray(int size) {
            return new StoreAddressVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
    }
}
