package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreVo implements Parcelable {
    public StoreAddressVo address;
    public LocationVo location;

    protected StoreVo(Parcel in) {
        address = in.readParcelable(StoreAddressVo.class.getClassLoader());
        location = in.readParcelable(LocationVo.class.getClassLoader());
    }

    public static final Creator<StoreVo> CREATOR = new Creator<StoreVo>() {
        @Override
        public StoreVo createFromParcel(Parcel in) {
            return new StoreVo(in);
        }

        @Override
        public StoreVo[] newArray(int size) {
            return new StoreVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(address, i);
        parcel.writeParcelable(location, i);
    }
}
