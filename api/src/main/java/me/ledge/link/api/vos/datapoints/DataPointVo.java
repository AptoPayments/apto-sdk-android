package me.ledge.link.api.vos.datapoints;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class DataPointVo {
    public enum DataPointType{
        PersonalName,
        PhoneNumber,
        Email,
        BirthDate,
        SSN,
        Address,
        Housing,
        Employment,
        Income,
        CreditScore,
        FinancialAccount;
    }

    @SerializedName("type")
    private final DataPointType mDataPointType;
    @SerializedName("verified")
    private boolean mVerified;
    private VerificationVo mVerification;

    /**
     * Default constructor
     */
    public DataPointVo() {
        this(null, false);
    }

    /**
     * Creates a new {@link DataPointVo} instance.
     */
    public DataPointVo(DataPointType type, boolean verified) {
        mDataPointType = type;
        mVerified = verified;
    }

    public DataPointVo(DataPointVo copy) {
        this.mDataPointType = copy.getType();
        this.mVerified = copy.isVerified();
        this.mVerification = copy.getVerification();
    }

    public void invalidateVerification() {
        mVerification = null;
        mVerified = false;
    }

    /**
     * @return DataPoint Type.
     */
    public DataPointType getType() {
        return mDataPointType;
    }

    public boolean isVerified() {
        return mVerified || (hasVerification() && getVerification().isVerified());
    }

    public boolean hasVerification() {
        return mVerification!=null;
    }

    public VerificationVo getVerification() {
        return mVerification;
    }

    public void setVerification(VerificationVo verification) {
        mVerification = verification;
    }

    public JsonObject toJSON() {
        JsonObject gsonObject = new JsonObject();

        if(this.hasVerification()) {
            gsonObject.add("verification", mVerification.toJSON());
        }
        return gsonObject;
    }
}