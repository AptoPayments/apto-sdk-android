package me.ledge.link.api.vos.requests.verifications;

import com.google.gson.annotations.SerializedName;

import me.ledge.link.api.vos.datapoints.DataPointVo;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to verify a user datapoint.
 * @author Adrian
 */
public class StartVerificationRequestVo extends UnauthorizedRequestVo {
    public DataPointVo.DataPointType datapoint_type;
    @SerializedName("datapoint")
    public DataPointVo data;
}
