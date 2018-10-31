package com.shiftpayments.link.sdk.sdk.tasks.financialaccounts;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.financialaccounts.ActivatePhysicalCardRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.financialaccounts.ActivatePhysicalCardResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class ActivatePhysicalCardTask extends ShiftApiTask<Void,Void,ActivatePhysicalCardResponseVo,ActivatePhysicalCardRequestVo> {

    private String mCardId;

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param request See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public ActivatePhysicalCardTask(ActivatePhysicalCardRequestVo request, String cardId, ShiftApiWrapper apiWrapper,
                                    ApiResponseHandler responseHandler) {
        super(request, apiWrapper, responseHandler);
        mCardId = cardId;
    }

    /** {@inheritDoc} */
    @Override
    protected ActivatePhysicalCardResponseVo callApi() throws ApiException {
        return getApiWrapper().activatePhysicalCard(mCardId, getRequestData());
    }
}



