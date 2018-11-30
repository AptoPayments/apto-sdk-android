package com.shiftpayments.link.sdk.api.vos.requests.users;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.users.StartOAuthTask;

/**
 * Request data to start oAuth.
 * @author Adrian
 */
public class StartOAuthRequestVo extends UnauthorizedRequestVo {
    public String provider;

    @SerializedName("base_uri")
    public String baseUri;

    public StartOAuthRequestVo(String provider, String baseUri) {
        this.provider = provider;
        this.baseUri = baseUri;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new StartOAuthTask(this, shiftApiWrapper, responseHandler);
    }
}
