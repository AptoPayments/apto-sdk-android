package com.shift.link.sdk.api.vos.datapoints;

import com.google.gson.JsonObject;

public class VerificationVo {
    public enum VerificationStatus{
        PENDING,
        PASSED,
        FAILED;
    }

    private VerificationStatus mVerificationStatus;
    private String mSecret;
    private String mVerificationId;
    private String mVerificationType;

    public VerificationVo() {
        mVerificationId = null;
        mSecret = null;
        mVerificationStatus = null;
        mVerificationType = null;
    }

    public VerificationVo(String verificationId) {
        mVerificationId = verificationId;
        mSecret = null;
        mVerificationStatus = null;
        mVerificationType = null;
    }

    public VerificationVo(String verificationId, String verificationType) {
        mVerificationId = verificationId;
        mSecret = null;
        mVerificationStatus = null;
        mVerificationType = verificationType;
    }

    /**
     * Creates a new {@link VerificationVo} instance.
     */
    public VerificationVo(String verificationId, VerificationStatus status, String secret) {
        mVerificationId = verificationId;
        mSecret = secret;
        mVerificationStatus = status;
    }

    public String getVerificationId() {
        return mVerificationId;
    }

    public void setVerificationId(String mVerificationId) {
        this.mVerificationId = mVerificationId;
    }

    public String getSecret() {
        return mSecret;
    }

    public void setSecret(String secret) {
        this.mSecret = secret;
    }

    public void setVerificationStatus(String verificationStatus) {
        try {
            this.mVerificationStatus = VerificationStatus.valueOf(verificationStatus.toUpperCase());
        }
        catch(IllegalArgumentException e) {
            this.mVerificationStatus = null;
        }
    }

    public String getVerificationType() {
        return mVerificationType;
    }

    public void setVerificationType(String verificationType) {
        this.mVerificationType = verificationType;
    }

    /**
     * @return is DataPoint Verified
     */
    public boolean isVerified() {
        return mVerificationStatus == VerificationStatus.PASSED;
    }

    public JsonObject toJSON() {
        JsonObject gsonObject = new JsonObject();;
        gsonObject.addProperty("secret", mSecret);
        gsonObject.addProperty("verification_id", String.valueOf(mVerificationId));
        gsonObject.addProperty("verification_type", mVerificationType);
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerificationVo that = (VerificationVo) o;

        if (mVerificationStatus != that.mVerificationStatus) return false;
        if (mSecret != null ? !mSecret.equals(that.mSecret) : that.mSecret != null) return false;
        return (mVerificationId != null ? !mVerificationId.equals(that.mVerificationId) : that.mVerificationId != null);
    }

    @Override
    public int hashCode() {
        int result = mVerificationStatus != null ? mVerificationStatus.hashCode() : 0;
        result = 31 * result + (mSecret != null ? mSecret.hashCode() : 0);
        result = 31 * result + (mVerificationId != null ? mVerificationId.hashCode() : 0);
        return result;
    }
}