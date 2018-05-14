package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MccVo implements Parcelable {

    @SerializedName("name")
    public String merchantCategoryName;

    @SerializedName("icon")
    public String merchantCategoryIcon;

    protected MccVo(Parcel in) {
        merchantCategoryName = in.readString();
        merchantCategoryIcon = in.readString();
    }

    public static final Creator<MccVo> CREATOR = new Creator<MccVo>() {
        @Override
        public MccVo createFromParcel(Parcel in) {
            return new MccVo(in);
        }

        @Override
        public MccVo[] newArray(int size) {
            return new MccVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(merchantCategoryName);
        parcel.writeString(merchantCategoryIcon);
    }
}
