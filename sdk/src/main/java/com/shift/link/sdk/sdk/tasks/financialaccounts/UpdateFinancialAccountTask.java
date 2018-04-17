package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to update a financial account
 * @author Pau
 */
public class UpdateFinancialAccountTask extends ShiftApiTask<Void,Void,UpdateFinancialAccountResponseVo,UpdateFinancialAccountRequestVo> {

    private String mAccountId;
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public UpdateFinancialAccountTask(UpdateFinancialAccountRequestVo requestData, String accountId, ShiftApiWrapper apiWrapper,
                       ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mAccountId = accountId;
    }

    /** {@inheritDoc} */
    @Override
    protected UpdateFinancialAccountResponseVo callApi() throws ApiException {
        return getApiWrapper().updateFinancialAccount(mAccountId, getRequestData());
    }
}