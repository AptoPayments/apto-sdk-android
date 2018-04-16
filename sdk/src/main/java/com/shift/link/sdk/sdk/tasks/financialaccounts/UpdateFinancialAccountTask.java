package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to update a financial account
 * @author Pau
 */
public class UpdateFinancialAccountTask extends LedgeLinkApiTask<Void,Void,UpdateFinancialAccountResponseVo,UpdateFinancialAccountRequestVo> {

    private String mAccountId;
    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public UpdateFinancialAccountTask(UpdateFinancialAccountRequestVo requestData, String accountId, LinkApiWrapper apiWrapper,
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