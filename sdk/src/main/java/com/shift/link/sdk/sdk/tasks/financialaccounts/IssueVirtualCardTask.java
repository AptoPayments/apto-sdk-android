package com.shift.link.sdk.sdk.tasks.financialaccounts;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.Card;
import com.shift.link.sdk.api.vos.requests.financialaccounts.IssueVirtualCardRequestVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to issue a virtual card.
 * @author Adrian
 */
public class IssueVirtualCardTask extends ShiftApiTask<Void,Void,Card,IssueVirtualCardRequestVo> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public IssueVirtualCardTask(IssueVirtualCardRequestVo requestData, ShiftApiWrapper apiWrapper,
                                ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected Card callApi() throws ApiException {
        return getApiWrapper().issueVirtualCard(getRequestData());
    }
}
