package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to retrieve the financial account's transactions
 * @author Adrian
 */
public class GetFinancialAccountTransactionsTask extends ShiftApiTask<Void, Void, TransactionListResponseVo, String> {
    private int mRows;
    private String mTransactionId;

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public GetFinancialAccountTransactionsTask(String requestData, int rows, String transactionId,
                                               ShiftApiWrapper apiWrapper,
                                               ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
        mRows = rows;
        mTransactionId = transactionId;
    }

    /** {@inheritDoc} */
    @Override
    protected TransactionListResponseVo callApi() throws ApiException {
        return getApiWrapper().getFinancialAccountTransactions(getRequestData(), mRows, mTransactionId);
    }
}
