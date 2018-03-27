package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.TransactionListResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to retrieve the financial account's transactions
 * @author Adrian
 */
public class GetFinancialAccountTransactionsTask extends LedgeLinkApiTask<Void, Void, TransactionListResponseVo, String> {
    private int mRows;
    private String mTransactionId;

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public GetFinancialAccountTransactionsTask(String requestData, int rows, String transactionId,
                                               LinkApiWrapper apiWrapper,
                                               ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
        mRows = rows;
        mTransactionId = transactionId;
    }

    /** {@inheritDoc} */
    @Override
    protected TransactionListResponseVo callApi() throws ApiException {
        return getApiWrapper().getFinancialAccountsTransactions(getRequestData(), mRows, mTransactionId);
    }
}
