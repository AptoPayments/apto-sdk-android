package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to issue a virtual card.
 * @author Adrian
 */
public class IssueVirtualCardTask extends LedgeLinkApiTask<Void, Void, Card, IssueVirtualCardRequestVo> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public IssueVirtualCardTask(IssueVirtualCardRequestVo requestData, LinkApiWrapper apiWrapper,
                                ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected Card callApi() throws ApiException {
        return getApiWrapper().issueVirtualCard(getRequestData());
    }
}
