package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to add a credit/debit card.
 * @author Adrian
 */
public class AddCardTask extends LedgeLinkApiTask<Void, Void, Card, Card> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public AddCardTask(Card requestData, LinkApiWrapper apiWrapper,
                       ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected Card callApi() throws ApiException {
        return getApiWrapper().addCard(getRequestData());
    }
}
