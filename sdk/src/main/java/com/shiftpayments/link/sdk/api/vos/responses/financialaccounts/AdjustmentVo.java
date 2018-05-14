package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AdjustmentVo implements Parcelable {

    public static final Creator<AdjustmentVo> CREATOR = new Creator<AdjustmentVo>() {
        @Override
        public AdjustmentVo createFromParcel(Parcel in) {
            return new AdjustmentVo(in);
        }

        @Override
        public AdjustmentVo[] newArray(int size) {
            return new AdjustmentVo[size];
        }
    };

    @SerializedName("adjustment_type")
    public AdjustmentType type;
    @SerializedName("created_at")
    public String creationTime;
    @SerializedName("id")
    public String id;
    @SerializedName("external_id")
    public String externalId;
    @SerializedName("exchange_rate")
    public String exchangeRate;
    @SerializedName("native_amount")
    public MoneyVo nativeAmount;
    @SerializedName("local_amount")
    public MoneyVo localAmount;
    @SerializedName("funding_source_name")
    public String fundingSourceName;

    public AdjustmentVo(Parcel in) {
        id = in.readString();
        externalId = in.readString();
        String rawType = in.readString();
        type = AdjustmentVo.AdjustmentType.valueOf(rawType == null ? "OTHER" : rawType);
        exchangeRate = in.readString();
        nativeAmount = in.readParcelable(MoneyVo.class.getClassLoader());
        creationTime = in.readString();
        localAmount = in.readParcelable(MoneyVo.class.getClassLoader());
        fundingSourceName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(externalId);
        parcel.writeString(type==null ? null : type.name());
        parcel.writeString(exchangeRate);
        parcel.writeParcelable(nativeAmount, i);
        parcel.writeString(creationTime);
        parcel.writeParcelable(localAmount, i);
        parcel.writeString(fundingSourceName);
    }

    public enum AdjustmentType {
        @SerializedName("capture")
        CAPTURE,
        @SerializedName("refund")
        REFUND,
        @SerializedName("hold")
        HOLD,
        @SerializedName("release")
        RELEASE,
        OTHER;
    }
}