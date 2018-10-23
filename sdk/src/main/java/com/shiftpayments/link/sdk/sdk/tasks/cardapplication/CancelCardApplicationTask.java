package com.shiftpayments.link.sdk.sdk.tasks.cardapplication;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.cardapplication.CancelCardApplicationRequest;
import com.shiftpayments.link.sdk.api.vos.responses.ApiEmptyResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class CancelCardApplicationTask extends ShiftApiTask<Void, Void, ApiEmptyResponseVo, CancelCardApplicationRequest> {

    private String mApplicationId;

    public CancelCardApplicationTask(CancelCardApplicationRequest request, String applicationId, ShiftApiWrapper apiWrapper, ApiResponseHandler responseHandler) {
        super(request, apiWrapper, responseHandler);
        this.mApplicationId = applicationId;
    }

    @Override
    protected ApiEmptyResponseVo callApi() throws ApiException {
        return getApiWrapper().deleteApplication(mApplicationId);
    }

}
