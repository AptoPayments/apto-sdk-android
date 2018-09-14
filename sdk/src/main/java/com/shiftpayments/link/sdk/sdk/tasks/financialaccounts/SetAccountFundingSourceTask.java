package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetFundingSourceRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to set a funding source
 * @author Adrian
 */
public class SetAccountFundingSourceTask extends ShiftApiTask<Void,Void,BalanceVo,Void> {

    private String mAccountId;
    private String mFundingSourceId;
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param accountId See {@link ShiftApiTask#ShiftApiTask}.
     * @param fundingSourceId See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public SetAccountFundingSourceTask(String accountId, String fundingSourceId, ShiftApiWrapper apiWrapper,
                                       ApiResponseHandler responseHandler) {

        super(null, apiWrapper, responseHandler);
        mAccountId = accountId;
        mFundingSourceId = fundingSourceId;
    }

    /** {@inheritDoc} */
    @Override
    protected BalanceVo callApi() throws ApiException {
        return getApiWrapper().setAccountFundingSource(mAccountId, new SetFundingSourceRequestVo(mFundingSourceId));
    }
}