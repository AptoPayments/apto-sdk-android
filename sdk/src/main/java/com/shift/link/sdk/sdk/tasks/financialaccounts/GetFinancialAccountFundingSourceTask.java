package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to retrieve the financial account's funding source
 * @author Adrian
 */
public class GetFinancialAccountFundingSourceTask extends ShiftApiTask<Void, Void, FundingSourceVo, String> {

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
    protected FundingSourceVo callApi() throws ApiException {
        return getApiWrapper().getFinancialAccountFundingSource(getRequestData());
    }
}
