package me.ledge.link.sdk.api.vos.requests.verifications;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to verify a user datapoint.
 * @author Adrian
 */
public class StartVerificationRequestVo extends UnauthorizedRequestVo {
    public me.ledge.link.sdk.api.vos.datapoints.DataPointVo.DataPointType datapoint_type;
    @SerializedName("datapoint")
    public me.ledge.link.sdk.api.vos.datapoints.DataPointVo data;
    public boolean show_verification_secret;
}
