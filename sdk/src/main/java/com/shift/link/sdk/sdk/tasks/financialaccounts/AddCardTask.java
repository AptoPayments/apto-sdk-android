package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to add a credit/debit card.
 * @author Adrian
 */
public class AddCardTask extends ShiftApiTask<Void, Void, Card, Card> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public AddCardTask(Card requestData, ShiftApiWrapper apiWrapper,
                       ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected Card callApi() throws ApiException {
        return getApiWrapper().addCard(getRequestData());
    }
}
