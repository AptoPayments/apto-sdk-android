package com.shiftpayments.link.sdk.api.vos.responses.financialaccounts;

import android.os.Parcel;
import android.os.Parcelable;

import com.shiftpayments.link.sdk.api.vos.responses.base.ListResponseVo;

/**
 * Created by adrian on 04/05/2018.
 */

public class TransferListResponseVo extends ListResponseVo<TransferVo[]> implements Parcelable {
    protected TransferListResponseVo(Parcel in) {
        super.data = (TransferVo[]) in.readArray(TransferVo.class.getClassLoader());
        super.has_more = in.readInt() == 1;
        super.page = in.readInt();
        super.rows = in.readInt();
        super.total_count = in.readInt();
        super.type = in.readString();
    }

    public static final Creator<TransferListResponseVo> CREATOR = new Creator<TransferListResponseVo>() {
        @Override
        public TransferListResponseVo createFromParcel(Parcel in) {
            return new TransferListResponseVo(in);
        }

        @Override
        public TransferListResponseVo[] newArray(int size) {
            return new TransferListResponseVo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeArray(super.data);
        parcel.writeInt(super.has_more ? 1 : 0);
        parcel.writeInt(super.page);
        parcel.writeInt(super.rows);
        parcel.writeInt(super.total_count);
        parcel.writeString(super.type);
    }
}
