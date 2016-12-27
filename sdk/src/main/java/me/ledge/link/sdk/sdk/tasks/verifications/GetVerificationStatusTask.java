package me.ledge.link.sdk.sdk.tasks.verifications;

import me.ledge.link.api.exceptions.ApiException;

import me.ledge.link.api.vos.responses.verifications.VerificationStatusResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to complete user's phone verification.
 * @author Adrian
 */
public class GetVerificationStatusTask extends LedgeLinkApiTask<Void, Void, VerificationStatusResponseVo, Integer> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public GetVerificationStatusTask(int requestData, LinkApiWrapper apiWrapper,
                                     ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected VerificationStatusResponseVo callApi() throws ApiException {
        return getApiWrapper().getVerificationStatus(getRequestData());
    }
}
