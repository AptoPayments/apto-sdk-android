package com.shiftpayments.link.sdk.api.vos.requests.base;

import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.RetryCallback;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Unauthorized API request data.
 * @author Wijnand
 */
public abstract class UnauthorizedRequestVo {

    /**
     * Public developer key.
     */
    public String public_developer_key;

    public transient RetryCallback retryCallback;

    public transient ApiResponseHandler mHandler;

    public abstract ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler);
}
