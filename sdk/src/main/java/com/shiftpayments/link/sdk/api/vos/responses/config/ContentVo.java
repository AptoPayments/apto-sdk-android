package com.shiftpayments.link.sdk.api.vos.responses.config;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Disclaimers data.
 * @author Adrian
 */
public class ContentVo implements Parcelable {

    public ContentVo(String type, String format, String value) {
        this.type = type;
        this.format = format;
        this.value = value;
    }

    public String type;

    public String format;

    public String value;

    public enum formatValues {
        plain_text,
        markdown,
        external_url
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.format);
        dest.writeString(this.value);
    }

    protected ContentVo(Parcel in) {
        this.type = in.readString();
        this.format = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<ContentVo> CREATOR = new Parcelable.Creator<ContentVo>() {
        @Override
        public ContentVo createFromParcel(Parcel source) {
            return new ContentVo(source);
        }

        @Override
        public ContentVo[] newArray(int size) {
            return new ContentVo[size];
        }
    };
}