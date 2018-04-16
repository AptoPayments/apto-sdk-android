package com.shift.link.sdk.sdk.tasks.verifications;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.responses.verifications.VerificationResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to complete user's phone verification.
 * @author Adrian
 */
public class RestartVerificationTask extends LedgeLinkApiTask<Void, Void, VerificationResponseVo, String> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public RestartVerificationTask(String requestData, LinkApiWrapper apiWrapper,
                                         ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected VerificationResponseVo callApi() throws ApiException {
        return getApiWrapper().restartVerification(getRequestData());
    }
}
