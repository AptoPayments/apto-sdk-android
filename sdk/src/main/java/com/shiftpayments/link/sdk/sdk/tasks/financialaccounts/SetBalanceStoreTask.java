package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.SetBalanceStoreRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to set the balance store
 * @author Adrian
 */
public class SetBalanceStoreTask extends ShiftApiTask<Void,Void,ApiEmptyResponseVo,SetBalanceStoreRequestVo> {
    private String mApplicationId;
    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public SetBalanceStoreTask(String applicationId, SetBalanceStoreRequestVo requestData, ShiftApiWrapper apiWrapper,
                               ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mApplicationId = applicationId;
    }

    /** {@inheritDoc} */
    @Override
    protected ApiEmptyResponseVo callApi() throws ApiException {
        return getApiWrapper().setBalanceStore(mApplicationId, getRequestData());
    }
}
