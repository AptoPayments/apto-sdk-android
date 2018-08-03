package com.shiftpayments.link.sdk.api.vos.requests.verifications;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.verifications.StartVerificationTask;

/**
 * Request data to verify a user datapoint.
 * @author Adrian
 */
public class StartVerificationRequestVo extends UnauthorizedRequestVo {
    public DataPointVo.DataPointType datapoint_type;
    @SerializedName("datapoint")
    public DataPointVo data;
    public boolean show_verification_secret;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new StartVerificationTask(this, shiftApiWrapper, responseHandler);
    }
}
