package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.responses.financialaccounts.UpdateFinancialAccountPinResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.UpdateFinancialAccountPinRequestVo;


/**
 * Created by pauteruel on 02/03/2018.
 */

public class UpdateFinancialAccountPinTask extends LedgeLinkApiTask<Void,Void,UpdateFinancialAccountPinResponseVo,UpdateFinancialAccountPinRequestVo> {
    private String mAccountId;
    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public UpdateFinancialAccountPinTask(UpdateFinancialAccountPinRequestVo requestData, String accountId, LinkApiWrapper apiWrapper,
                                      ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
        mAccountId = accountId;
    }

    /** {@inheritDoc} */
    @Override
    protected UpdateFinancialAccountPinResponseVo callApi() throws ApiException {
        return getApiWrapper().updateFinancialAccountPin(mAccountId, getRequestData());
    }
}



