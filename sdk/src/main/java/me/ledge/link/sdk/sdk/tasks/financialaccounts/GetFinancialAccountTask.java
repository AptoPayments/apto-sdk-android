package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.FinancialAccountVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to retrieve the current user's financial accounts
 * @author Adrian
 */
public class GetFinancialAccountTask extends LedgeLinkApiTask<Void, Void, FinancialAccountVo, String> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public GetFinancialAccountTask(String requestData, LinkApiWrapper apiWrapper,
                                   ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected FinancialAccountVo callApi() throws ApiException {
        return getApiWrapper().getFinancialAccount(getRequestData());
    }
}
