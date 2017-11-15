package me.ledge.link.sdk.sdk.tasks.loanapplication;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to get the current application status
 * @author Adrian
 */
public class GetLoanApplicationStatusTask
        extends LedgeLinkApiTask<Void, Void, LoanApplicationDetailsResponseVo, String> {

    /**
     * Creates a new {@link GetLoanApplicationStatusTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public GetLoanApplicationStatusTask(String requestData, LinkApiWrapper apiWrapper,
                                        ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationDetailsResponseVo callApi() throws ApiException {
        return getApiWrapper().getApplicationStatus(getRequestData());
    }
}
