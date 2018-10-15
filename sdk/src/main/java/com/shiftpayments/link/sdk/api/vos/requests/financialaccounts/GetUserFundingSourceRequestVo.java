package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.GetUserFundingSourcesTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class GetUserFundingSourceRequestVo extends UnauthorizedRequestVo {

    public String cardId;

    public GetUserFundingSourceRequestVo(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetUserFundingSourcesTask(cardId, shiftApiWrapper, responseHandler);
    }
}
