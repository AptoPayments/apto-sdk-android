package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationVo implements Parcelable {
    public double latitude;
    public double longitude;

    protected LocationVo(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LocationVo> CREATOR = new Creator<LocationVo>() {
        @Override
        public LocationVo createFromParcel(Parcel in) {
            return new LocationVo(in);
        }

        @Override
        public LocationVo[] newArray(int size) {
            return new LocationVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
