package com.shift.link.sdk.sdk.tasks.verifications;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.verifications.VerificationRequestVo;
import com.shift.link.sdk.api.vos.responses.verifications.FinishVerificationResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to complete user's phone verification.
 * @author Adrian
 */
public class CompleteVerificationTask extends LedgeLinkApiTask<Void, Void, FinishVerificationResponseVo, VerificationRequestVo> {

    private String mVerificationId;
    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public CompleteVerificationTask(VerificationRequestVo requestData, String verificationId, LinkApiWrapper apiWrapper,
                                    ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
        mVerificationId = verificationId;
    }

    /** {@inheritDoc} */
    @Override
    protected FinishVerificationResponseVo callApi() throws ApiException {
        return getApiWrapper().completeVerification(getRequestData(), mVerificationId);
    }
}
