package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.AcceptDisclaimerTask;

/**
 * Request data to accept a disclaimer
 * @author ADrian
 */
public class AcceptDisclaimerRequestVo  extends UnauthorizedRequestVo {
    @SerializedName("workflow_object_id")
    public String workflowObjectId;

    @SerializedName("action_id")
    public String actionId;

    public AcceptDisclaimerRequestVo(String workflowObjectId, String actionId) {
        this.workflowObjectId = workflowObjectId;
        this.actionId = actionId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new AcceptDisclaimerTask(this, shiftApiWrapper, responseHandler);
    }
}
