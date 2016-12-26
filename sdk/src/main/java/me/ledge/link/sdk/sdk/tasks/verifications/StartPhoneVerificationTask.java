package me.ledge.link.sdk.sdk.tasks.verifications;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.verifications.PhoneVerificationRequestVo;
import me.ledge.link.api.vos.responses.verifications.StartVerificationResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to start user's phone verification.
 * @author Adrian
 */
public class StartPhoneVerificationTask extends LedgeLinkApiTask<Void, Void, StartVerificationResponseVo, PhoneVerificationRequestVo> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public StartPhoneVerificationTask(PhoneVerificationRequestVo requestData, LinkApiWrapper apiWrapper,
                                      ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected StartVerificationResponseVo callApi() throws ApiException {
        return getApiWrapper().startPhoneVerification(getRequestData());
    }
}
