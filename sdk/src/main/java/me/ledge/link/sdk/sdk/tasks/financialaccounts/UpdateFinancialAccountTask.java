package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.financialaccounts.UpdateFinancialAccountRequestVo;
import me.ledge.link.api.vos.responses.financialaccounts.UpdateFinancialAccountResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to update a financial account
 * @author Pau
 */
public class UpdateFinancialAccountTask extends LedgeLinkApiTask<Void,Void,UpdateFinancialAccountResponseVo,UpdateFinancialAccountRequestVo> {

    private String mAccountId;
    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public UpdateFinancialAccountTask(UpdateFinancialAccountRequestVo requestData, String accountId, LinkApiWrapper apiWrapper,
                       ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mAccountId = accountId;
    }

    /** {@inheritDoc} */
    @Override
    protected UpdateFinancialAccountResponseVo callApi() throws ApiException {
        return getApiWrapper().updateFinancialAccount(mAccountId, getRequestData());
    }
}