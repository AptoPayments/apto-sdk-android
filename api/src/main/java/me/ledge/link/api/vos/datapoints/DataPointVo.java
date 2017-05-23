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
        FinancialAccount,
        PayDayLoan,
        MemberOfArmedForces,
        TimeAtAddress;
    }

    @SerializedName("type")
    private final DataPointType mDataPointType;
    @SerializedName("verified")
    private boolean mVerified;
    private VerificationVo mVerification;
    private boolean mNotSpecified;

    /**
     * Default constructor
     */
    public DataPointVo() {
        this(null, false, false);
    }

    /**
     * Creates a new {@link DataPointVo} instance.
     */
    public DataPointVo(DataPointType type, boolean verified, boolean notSpecified) {
        mDataPointType = type;
        mVerified = verified;
        mNotSpecified = notSpecified;
    }

    public DataPointVo(DataPointVo copy) {
        this.mDataPointType = copy.getType();
        this.mVerified = copy.isVerified();
        this.mVerification = copy.getVerification();
        this.mNotSpecified = copy.isNotSpecified();
    }

    public void invalidateVerification() {
        mVerification = null;
        mVerified = false;
    }

    public DataPointType getType() {
        return mDataPointType;
    }

    public boolean isNotSpecified() {
        return mNotSpecified;
    }

    public void setNotSpecified(boolean notSpecified) {
        mNotSpecified = notSpecified;
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
        gsonObject.addProperty("not_specified", mNotSpecified);
        return gsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataPointVo that = (DataPointVo) o;

        if (mVerified != that.mVerified) return false;
        if (mNotSpecified != that.mNotSpecified) return false;
        if (mDataPointType != that.mDataPointType) return false;
        return mVerification != null ? mVerification.equals(that.mVerification) : that.mVerification == null;

    }

    @Override
    public int hashCode() {
        int result = mDataPointType != null ? mDataPointType.hashCode() : 0;
        result = 31 * result + (mVerified ? 1 : 0);
        result = 31 * result + (mVerification != null ? mVerification.hashCode() : 0);
        result = 31 * result + (mNotSpecified ? 1 : 0);
        return result;
    }
}