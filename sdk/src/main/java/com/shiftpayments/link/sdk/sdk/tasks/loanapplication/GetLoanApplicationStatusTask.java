package com.shiftpayments.link.sdk.sdk.tasks.loanapplication;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to get the current application status
 * @author Adrian
 */
public class GetLoanApplicationStatusTask
        extends ShiftApiTask<Void, Void, LoanApplicationDetailsResponseVo, String> {

    /**
     * Creates a new {@link GetLoanApplicationStatusTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public GetLoanApplicationStatusTask(String requestData, ShiftApiWrapper apiWrapper,
                                        ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationDetailsResponseVo callApi() throws ApiException {
        return getApiWrapper().getLoanApplicationStatus(getRequestData());
    }
}
