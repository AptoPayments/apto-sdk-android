package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountFundingSourceTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class GetFinancialAccountFundingSourceRequestVo extends UnauthorizedRequestVo {

    private String mAccountId;

    public GetFinancialAccountFundingSourceRequestVo(String accountId) {
        mAccountId = accountId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetFinancialAccountFundingSourceTask(mAccountId, shiftApiWrapper, responseHandler);
    }
}
