package com.shiftpayments.link.sdk.api.vos.requests.base;

import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Generic list request.
 * TODO: Move to API common project.
 * @author wijnand
 */
public class ListRequestVo<DataType> extends UnauthorizedRequestVo {

    private static final int DEFAULT_ROWS = 10;

    public int page = 0;
    public int rows = DEFAULT_ROWS;
    public DataType data;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return null;
    }
}
