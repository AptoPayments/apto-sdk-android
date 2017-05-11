package me.ledge.link.api.vos.responses.config;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.datapoints.DataPointVo;

/**
 * Info about the required DataPoint.
 * @author Adrian
 */
public class RequiredDataPointVo {

    @SerializedName("datapoint_type")
    public DataPointVo.DataPointType type;

    @SerializedName("verification_required")
    public boolean verificationRequired;

    @SerializedName("not_specified_allowed")
    public boolean notSpecifiedAllowed;

    public RequiredDataPointVo(DataPointVo.DataPointType type) {
        this.type = type;
        this.verificationRequired = false;
        this.notSpecifiedAllowed = false;
    }

    public RequiredDataPointVo(DataPointVo.DataPointType type, Boolean verificationRequired,
                               Boolean notSpecifiedAllowed) {
        this.type = type;
        this.verificationRequired = verificationRequired;
        this.notSpecifiedAllowed = notSpecifiedAllowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequiredDataPointVo that = (RequiredDataPointVo) o;

        return type != null ? type.equals(that.type) : that.type == null;

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
