package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

public class MerchantVo implements Parcelable {
    public String name;
    public MccVo mcc;

    protected MerchantVo(Parcel in) {
        name = in.readString();
        mcc = in.readParcelable(MccVo.class.getClassLoader());
    }

    public static final Creator<MerchantVo> CREATOR = new Creator<MerchantVo>() {
        @Override
        public MerchantVo createFromParcel(Parcel in) {
            return new MerchantVo(in);
        }

        @Override
        public MerchantVo[] newArray(int size) {
            return new MerchantVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeParcelable(mcc, i);
    }
}
