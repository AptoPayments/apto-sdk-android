package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class IdDocument extends DataPointVo {

    public enum IdDocumentType {
        @SerializedName("ssn")
        SSN("SSN"),
        @SerializedName("identity_card")
        IDENTITY_CARD("ID Card"),
        @SerializedName("passport")
        PASSPORT("Passport"),
        @SerializedName("drivers_license")
        DRIVERS_LICENSE("Driving license");

        private final String description;

        private IdDocumentType(String value) {
            description = value;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private IdDocumentType mType;
    private String mValue;
    private String mCountry;

    public IdDocument() {
        this(null, null, null, false, false);
    }

    public IdDocument(IdDocumentType type, String value, String country, boolean verified, boolean notSpecified) {
        super(DataPointType.IdDocument, verified, notSpecified);
        mValue = value;
        mType = type;
        mCountry = country;
    }

    public IdDocumentType getIdType() {
        return mType;
    }

    public void setIdType(IdDocumentType type) {
        mType = type;
    }

    public String getIdValue() {
        return mValue;
    }

    public void setIdValue(String value) {
        mValue = value;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "id_document");
        gsonObject.addProperty("doc_type", mType.name().toLowerCase());
        gsonObject.addProperty("value", mValue);
        gsonObject.addProperty("country", mCountry);
        return gsonObject;
    }

    @Override
    public String toString() {
        return mValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        IdDocument that = (IdDocument) o;

        if (mType != that.mType) return false;
        if (mValue != null ? !mValue.equals(that.mValue) : that.mValue != null) return false;
        return mCountry != null ? mCountry.equals(that.mCountry) : that.mCountry == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        result = 31 * result + (mCountry != null ? mCountry.hashCode() : 0);
        return result;
    }
}