package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.api.vos.requests.financialaccounts.UpdateFinancialAccountPinVo;


/**
 * Created by pauteruel on 02/03/2018.
 */

public class UpdateFinancialAccountPinTask extends LedgeLinkApiTask<Void, Void, Card, UpdateFinancialAccountPinVo>{
    private String mAccountId;
    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public UpdateFinancialAccountPinTask(UpdateFinancialAccountPinVo requestData, String accountId, LinkApiWrapper apiWrapper,
                                      ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
        mAccountId = accountId;
    }

    /** {@inheritDoc} */
    @Override
    protected Card callApi() throws ApiException {
        return getApiWrapper().updateFinancialAccountPin(mAccountId, getRequestData());
    }
}



