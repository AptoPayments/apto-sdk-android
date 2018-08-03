package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.shiftpayments.link.sdk.api.vos.responses.base.ListResponseVo;

/**
 * Created by adrian on 04/05/2018.
 */

public class AdjustmentListResponseVo extends ListResponseVo<AdjustmentVo[]> implements Parcelable {

    protected AdjustmentListResponseVo(Parcel in) {
        super.data = in.createTypedArray(AdjustmentVo.CREATOR);
        super.has_more = in.readInt() == 1;
        super.page = in.readInt();
        super.rows = in.readInt();
        super.total_count = in.readInt();
        super.type = in.readString();
    }

    public static final Creator<AdjustmentListResponseVo> CREATOR = new Creator<AdjustmentListResponseVo>() {
        @Override
        public AdjustmentListResponseVo createFromParcel(Parcel in) {
            return new AdjustmentListResponseVo(in);
        }

        @Override
        public AdjustmentListResponseVo[] newArray(int size) {
            return new AdjustmentListResponseVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(super.data, i);
        parcel.writeInt(super.has_more ? 1 : 0);
        parcel.writeInt(super.page);
        parcel.writeInt(super.rows);
        parcel.writeInt(super.total_count);
        parcel.writeString(super.type);
    }
}
