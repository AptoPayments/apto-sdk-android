package me.ledge.link.sdk.sdk.tasks.offers;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.offers.InitialOffersRequestVo;
import me.ledge.link.api.vos.responses.offers.InitialOffersResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;

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
