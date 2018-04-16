package com.shift.link.sdk.sdk.tasks.loanapplication;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to create a new loan application.
 * @author Wijnand
 */
public class CreateLoanApplicationTask extends LedgeLinkApiTask<Void, Void, LoanApplicationDetailsResponseVo, String> {

    /**
     * Creates a new {@link CreateLoanApplicationTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public CreateLoanApplicationTask(String requestData, LinkApiWrapper apiWrapper,
            ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationDetailsResponseVo callApi() throws ApiException {
        return getApiWrapper().createLoanApplication(getRequestData());
    }
}
