package com.shiftpayments.link.sdk.sdk.tasks.offers;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to get the initial list of loan offers.
 * @author wijnand
 */
public class InitialOffersTask extends ShiftApiTask<Void, Void, InitialOffersResponseVo, InitialOffersRequestVo> {

    /**
     * Creates a new {@link InitialOffersTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public InitialOffersTask(InitialOffersRequestVo requestData, ShiftApiWrapper apiWrapper,
            ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected InitialOffersResponseVo callApi() throws ApiException {
        return getApiWrapper().getInitialOffers(getRequestData());
    }
}
