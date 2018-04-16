package com.shift.link.sdk.sdk.tasks.offers;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.offers.InitialOffersRequestVo;
import com.shift.link.sdk.api.vos.responses.offers.InitialOffersResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to get the initial list of loan offers.
 * @author wijnand
 */
public class InitialOffersTask extends LedgeLinkApiTask<Void, Void, InitialOffersResponseVo, InitialOffersRequestVo> {

    /**
     * Creates a new {@link InitialOffersTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public InitialOffersTask(InitialOffersRequestVo requestData, LinkApiWrapper apiWrapper,
            ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected InitialOffersResponseVo callApi() throws ApiException {
        return getApiWrapper().getInitialOffers(getRequestData());
    }
}
