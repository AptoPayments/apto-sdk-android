package me.ledge.link.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

/**
 * Info about the required DataPoint.
 * @author Adrian
 */
public class RequiredDataPointVo {

    @SerializedName("datapoint_type")
    public int type;

    @SerializedName("verification_required")
    public boolean verificationRequired;

    public RequiredDataPointVo(int type) {
        this.type = type;
        this.verificationRequired = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequiredDataPointVo that = (RequiredDataPointVo) o;

        return type == that.type;

    }

    @Override
    public int hashCode() {
        return type;
    }
}
