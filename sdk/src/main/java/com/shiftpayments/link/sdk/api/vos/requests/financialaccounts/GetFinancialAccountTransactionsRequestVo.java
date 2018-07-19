package com.shiftpayments.link.sdk.api.vos.requests.financialaccounts;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.financialaccounts.GetFinancialAccountTransactionsTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class GetFinancialAccountTransactionsRequestVo extends UnauthorizedRequestVo {

    private String mAccountId;
    private int rows;
    private String lastTransactionId;

    public GetFinancialAccountTransactionsRequestVo(String mAccountId, int rows, String lastTransactionId) {
        this.mAccountId = mAccountId;
        this.rows = rows;
        this.lastTransactionId = lastTransactionId;
    }

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        return new GetFinancialAccountTransactionsTask(mAccountId, rows, lastTransactionId, shiftApiWrapper, responseHandler);
    }
}
