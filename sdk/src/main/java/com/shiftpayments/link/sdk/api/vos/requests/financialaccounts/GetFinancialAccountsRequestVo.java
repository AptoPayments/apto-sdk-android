package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountsTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class GetFinancialAccountsRequestVo extends UnauthorizedRequestVo {
    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetFinancialAccountsTask(this, shiftApiWrapper, responseHandler);
    }
}
