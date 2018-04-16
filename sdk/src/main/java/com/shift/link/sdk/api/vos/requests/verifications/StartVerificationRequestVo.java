package com.shift.link.sdk.api.vos.requests.verifications;

import com.google.gson.annotations.SerializedName;
import com.shift.link.sdk.api.vos.datapoints.DataPointVo;

import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to verify a user datapoint.
 * @author Adrian
 */
public class StartVerificationRequestVo extends UnauthorizedRequestVo {
    public DataPointVo.DataPointType datapoint_type;
    @SerializedName("datapoint")
    public DataPointVo data;
    public boolean show_verification_secret;
}
