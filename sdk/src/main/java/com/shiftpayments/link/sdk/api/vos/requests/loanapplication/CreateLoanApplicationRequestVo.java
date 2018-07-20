package com.shiftpayments.link.sdk.api.vos.requests.loanapplication;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.sdk.tasks.loanapplication.CreateLoanApplicationTask;

public class CreateLoanApplicationRequestVo extends UnauthorizedRequestVo {

    String offerId;

    public CreateLoanApplicationRequestVo(String offerId) {
        this.offerId = offerId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new CreateLoanApplicationTask(offerId, shiftApiWrapper, responseHandler);
    }
}
