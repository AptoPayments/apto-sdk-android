package me.ledge.link.sdk.sdk.tasks.financialaccounts;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.sdk.api.vos.responses.users.UserDataListResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to retrieve the current user's financial accounts
 * @author Adrian
 */
public class GetFinancialAccountsTask extends LedgeLinkApiTask<Void, Void, DataPointList, UnauthorizedRequestVo> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public GetFinancialAccountsTask(UnauthorizedRequestVo requestData, LinkApiWrapper apiWrapper,
                                    ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected DataPointList callApi() throws ApiException {
        UserDataListResponseVo response = getApiWrapper().getFinancialAccounts(getRequestData());
        DataPointList dataPointList = new DataPointList();
        for(DataPointVo d : response.data) {
            if(d != null) {
                dataPointList.add(d);
            }
        }

        return dataPointList;
    }
}
