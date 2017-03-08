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
}
