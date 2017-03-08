package me.ledge.link.sdk.sdk.tasks.config;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.api.vos.responses.config.LinkConfigResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;

/**
 * A concrete {@link LedgeLinkApiTask} to display loan purposes.
 * @author wijnand
 */
public class LinkConfigTask extends LedgeLinkApiTask<Void, Void, LinkConfigResponseVo, UnauthorizedRequestVo> {

    /**
     * Creates a new {@link LinkConfigTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public LinkConfigTask(UnauthorizedRequestVo requestData, LinkApiWrapper apiWrapper,
                          ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LinkConfigResponseVo callApi() throws ApiException {
        return getApiWrapper().getLinkConfig(getRequestData());
    }
}
