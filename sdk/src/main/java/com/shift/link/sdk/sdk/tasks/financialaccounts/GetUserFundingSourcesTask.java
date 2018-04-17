package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shift.link.sdk.api.vos.responses.financialaccounts.FundingSourceListVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to retrieve the user's funding sources
 * @author Adrian
 */
public class GetUserFundingSourcesTask extends ShiftApiTask<Void, Void, FundingSourceListVo, UnauthorizedRequestVo> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public GetUserFundingSourcesTask(UnauthorizedRequestVo requestData,
                                     ShiftApiWrapper apiWrapper,
                                     ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected FundingSourceListVo callApi() throws ApiException {
        return getApiWrapper().getUserFundingSources(getRequestData());
    }
}
