package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.EnableFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Created by Adrian on 21/05/2018.
 */

public class EnableFinancialAccountTask extends ShiftApiTask<Void,Void,EnableFinancialAccountResponseVo,String> {
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param accountId See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public EnableFinancialAccountTask(String accountId, ShiftApiWrapper apiWrapper,
                                      ApiResponseHandler responseHandler) {
        super(accountId, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected EnableFinancialAccountResponseVo callApi() throws ApiException {
        return getApiWrapper().enableFinancialAccount(getRequestData());
    }
}



