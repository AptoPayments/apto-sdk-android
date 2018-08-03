package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.EnableFinancialAccountTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class EnableFinancialAccountRequestVo extends UnauthorizedRequestVo {

    private String mAccountId;

    public EnableFinancialAccountRequestVo(String accountId) {
        mAccountId = accountId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new EnableFinancialAccountTask(mAccountId, shiftApiWrapper, responseHandler);
    }
}
