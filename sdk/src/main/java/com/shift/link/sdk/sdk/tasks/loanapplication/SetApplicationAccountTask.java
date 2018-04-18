package com.shift.link.sdk.sdk.tasks.loanapplication;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to link an account to the given application
 * @author Adrian
 */
public class SetApplicationAccountTask
        extends ShiftApiTask<Void, Void, LoanApplicationDetailsResponseVo, ApplicationAccountRequestVo> {

    private String mApplicationId;
    /**
     * Creates a new {@link SetApplicationAccountTask} instance.
     * @param requestData API request data.
     * @param applicationId Application ID
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public SetApplicationAccountTask(ApplicationAccountRequestVo requestData, String applicationId, ShiftApiWrapper apiWrapper,
                                     ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mApplicationId = applicationId;
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationDetailsResponseVo callApi() throws ApiException {
        return getApiWrapper().setApplicationAccount(getRequestData(), mApplicationId);
    }
}
