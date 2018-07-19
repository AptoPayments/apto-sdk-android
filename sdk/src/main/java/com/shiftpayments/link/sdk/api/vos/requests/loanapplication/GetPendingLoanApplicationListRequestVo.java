package com.shiftpayments.link.sdk.api.vos.requests.loanapplication;

import com.shiftpayments.link.sdk.api.vos.requests.base.ListRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.loanapplication.ListPendingLoanApplicationsTask;

public class GetPendingLoanApplicationListRequestVo extends UnauthorizedRequestVo {

    ListRequestVo data;

    public GetPendingLoanApplicationListRequestVo(ListRequestVo data) {
        this.data = data;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new ListPendingLoanApplicationsTask(data, shiftApiWrapper, responseHandler);
    }
}
