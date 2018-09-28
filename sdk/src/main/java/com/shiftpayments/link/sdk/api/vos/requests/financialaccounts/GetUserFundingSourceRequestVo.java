package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.GetUserFundingSourcesTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class GetUserFundingSourceRequestVo extends UnauthorizedRequestVo {

    private String mAccountId;

    public GetUserFundingSourceRequestVo(String accountId) {
        mAccountId = accountId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetUserFundingSourcesTask(mAccountId, shiftApiWrapper, responseHandler);
    }
}
