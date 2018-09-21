package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to retrieve the financial account's funding source
 * @author Adrian
 */
public class GetFinancialAccountFundingSourceTask extends ShiftApiTask<Void, Void, BalanceVo, String> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public GetFinancialAccountFundingSourceTask(String requestData,
                                                ShiftApiWrapper apiWrapper,
                                                ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected BalanceVo callApi() throws ApiException {
        return getApiWrapper().getFinancialAccountFundingSource(getRequestData());
    }
}
