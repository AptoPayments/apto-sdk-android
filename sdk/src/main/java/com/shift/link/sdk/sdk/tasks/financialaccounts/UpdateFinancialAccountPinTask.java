package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;


/**
 * Created by pauteruel on 02/03/2018.
 */

public class UpdateFinancialAccountPinTask extends ShiftApiTask<Void,Void,UpdateFinancialAccountPinResponseVo,UpdateFinancialAccountPinRequestVo> {
    private String mAccountId;
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public UpdateFinancialAccountPinTask(UpdateFinancialAccountPinRequestVo requestData, String accountId, ShiftApiWrapper apiWrapper,
                                      ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
        mAccountId = accountId;
    }

    /** {@inheritDoc} */
    @Override
    protected UpdateFinancialAccountPinResponseVo callApi() throws ApiException {
        return getApiWrapper().updateFinancialAccountPin(mAccountId, getRequestData());
    }
}



