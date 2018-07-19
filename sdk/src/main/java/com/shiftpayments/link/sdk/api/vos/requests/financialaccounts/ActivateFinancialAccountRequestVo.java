package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.ActivateFinancialAccountTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class ActivateFinancialAccountRequestVo extends UnauthorizedRequestVo {

    private String mAccountId;

    public ActivateFinancialAccountRequestVo(String accountId) {
        mAccountId = accountId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new ActivateFinancialAccountTask(mAccountId, shiftApiWrapper, responseHandler);
    }
}
