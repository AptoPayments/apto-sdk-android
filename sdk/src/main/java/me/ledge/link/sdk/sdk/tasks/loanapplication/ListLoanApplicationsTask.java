package me.ledge.link.sdk.sdk.tasks.loanapplication;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.base.ListRequestVo;
import me.ledge.link.api.vos.responses.loanapplication.LoanApplicationsListResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to list open loan applications.
 * @author Wijnand
 */
public class ListLoanApplicationsTask
        extends LedgeLinkApiTask<Void, Void, LoanApplicationsListResponseVo, ListRequestVo> {

    /**
     * Creates a new {@link ListLoanApplicationsTask} instance.
     * @param requestData API request data.
     * @param apiWrapper The API wrapper instance to make API calls.
     * @param responseHandler The response handler instance used to publish results.
     */
    public ListLoanApplicationsTask(ListRequestVo requestData, LinkApiWrapper apiWrapper,
            ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LoanApplicationsListResponseVo callApi() throws ApiException {
        return getApiWrapper().getLoanApplicationsList(getRequestData());
    }
}
