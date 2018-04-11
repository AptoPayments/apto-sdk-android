package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.FundingSourceVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to retrieve the financial account's funding source
 * @author Adrian
 */
public class GetFinancialAccountFundingSourceTask extends LedgeLinkApiTask<Void, Void, FundingSourceVo, String> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public GetFinancialAccountFundingSourceTask(String requestData,
                                                LinkApiWrapper apiWrapper,
                                                ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected FundingSourceVo callApi() throws ApiException {
        return getApiWrapper().getFinancialAccountFundingSource(getRequestData());
    }
}
