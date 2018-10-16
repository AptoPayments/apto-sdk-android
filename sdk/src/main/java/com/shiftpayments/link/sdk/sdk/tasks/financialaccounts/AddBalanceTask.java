package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.AddBalanceRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.BalanceVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to add a new Balance
 * @author Adrian
 */
public class AddBalanceTask extends ShiftApiTask<Void,Void,BalanceVo,AddBalanceRequestVo> {

    private String mCardId;
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public AddBalanceTask(AddBalanceRequestVo requestData, String cardId, ShiftApiWrapper apiWrapper,
                          ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
        mCardId = cardId;
    }

    /** {@inheritDoc} */
    @Override
    protected BalanceVo callApi() throws ApiException {
        return getApiWrapper().addUserBalance(mCardId, getRequestData());
    }
}