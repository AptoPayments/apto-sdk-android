package com.shiftpayments.link.sdk.api.vos.requests.loanapplication;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.loanapplication.GetLoanApplicationStatusTask;

public class GetLoanApplicationStatusRequestVo extends UnauthorizedRequestVo {

    String applicationId;

    public GetLoanApplicationStatusRequestVo(String applicationId) {
        this.applicationId = applicationId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetLoanApplicationStatusTask(applicationId, shiftApiWrapper, responseHandler);
    }
}
