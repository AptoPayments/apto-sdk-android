package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.ActivateFinancialAccountResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Created by Adrian on 21/05/2018.
 */

public class ActivateFinancialAccountPinTask extends ShiftApiTask<Void,Void,ActivateFinancialAccountResponseVo,String> {
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param accountId See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public ActivateFinancialAccountPinTask(String accountId, ShiftApiWrapper apiWrapper,
                                           ApiResponseHandler responseHandler) {
        super(accountId, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected ActivateFinancialAccountResponseVo callApi() throws ApiException {
        return getApiWrapper().activateFinancialAccount(getRequestData());
    }
}



