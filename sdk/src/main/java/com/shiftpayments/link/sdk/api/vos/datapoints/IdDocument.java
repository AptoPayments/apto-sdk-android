package com.shiftpayments.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;

public class IdDocument extends DataPointVo {

    public enum IdDocumentType {
        SSN,
        IDENTITY_CARD,
        PASSPORT,
        DRIVER_LICENSE;


        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private IdDocumentType mType;
    private String mValue;
    private String mCountry;
    private static final int EXPECTED_SSN_LENGTH = 9;

    public IdDocument() {
        this(null, null, false, false);
    }

    public IdDocument(IdDocumentType type, String value, boolean verified, boolean notSpecified) {
        super(DataPointType.IdDocument, verified, notSpecified);
        mValue = value;
        mType = type;
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

    @Override
    public JsonObject toJSON() {
        JsonObject gsonObject = super.toJSON();
        gsonObject.addProperty("data_type", "id_document");
        gsonObject.addProperty("doc_type", mType.toString());
        gsonObject.addProperty("doc_value", mValue);
        // TODO
        gsonObject.addProperty("country", "US");
        return gsonObject;
    }

    @Override
    public String toString() {
        if(mType.equals(IdDocumentType.SSN)) {
            StringBuilder outputBuffer = new StringBuilder(EXPECTED_SSN_LENGTH);
            for (int i = 0; i < EXPECTED_SSN_LENGTH; i++){
                outputBuffer.append("*");
            }
            return outputBuffer.toString();
        }
        else {
            return mValue;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if(!super.equals(o)) return false;
        IdDocument idDocument = (IdDocument) o;

        return mValue != null ? mValue.equals(idDocument.mValue) : idDocument.mValue == null;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (mValue != null ? mValue.hashCode() : 0);
    }
}