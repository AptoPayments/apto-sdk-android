package me.ledge.link.sdk.sdk.tasks.loanapplication;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.requests.financialaccounts.ApplicationAccountRequestVo;
import me.ledge.link.sdk.api.vos.responses.loanapplication.LoanApplicationDetailsResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to link an account to the given application
 * @author Adrian
 */
public class SetApplicationAccountTask
        extends LedgeLinkApiTask<Void, Void, LoanApplicationDetailsResponseVo, ApplicationAccountRequestVo> {

    private String mApplicationId;
    /**
     * Creates a new {@link SetApplicationAccountTask} instance.
     * @param requestData API request data.
     * @param applicationId Application ID
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public SetApplicationAccountTask(ApplicationAccountRequestVo requestData, String applicationId, LinkApiWrapper apiWrapper,
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
