package me.ledge.link.sdk.sdk.tasks.verifications;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.verifications.StartVerificationRequestVo;
import me.ledge.link.api.vos.responses.verifications.StartVerificationResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.storages.AutomationStorage;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to start a verification.
 * @author Adrian
 */
public class StartVerificationTask extends LedgeLinkApiTask<Void, Void, StartVerificationResponseVo, StartVerificationRequestVo> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public StartVerificationTask(StartVerificationRequestVo requestData, LinkApiWrapper apiWrapper,
                                 ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected StartVerificationResponseVo callApi() throws ApiException {
        StartVerificationResponseVo response = getApiWrapper().startVerification(getRequestData());
        AutomationStorage.getInstance().verificationSecret = response.secret;
        return response;
    }
}
