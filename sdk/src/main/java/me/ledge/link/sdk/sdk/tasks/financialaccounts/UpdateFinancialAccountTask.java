package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.Card;
import me.ledge.link.api.vos.requests.financialaccounts.ManageCardRequestVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to update a financial account
 * @author Pau
 */
public class UpdateFinancialAccountTask extends LedgeLinkApiTask<Void, Void, Card, ManageCardRequestVo> {

    private String mAccountId;
    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public UpdateFinancialAccountTask(ManageCardRequestVo requestData, String mAccountId, LinkApiWrapper apiWrapper,
                       ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mAccountId = mAccountId;
    }

    /** {@inheritDoc} */
    @Override
    protected Card callApi() throws ApiException {
        return getApiWrapper().updateFinancialAccount(mAccountId, getRequestData());
    }
}