package com.shift.link.sdk.sdk.tasks.verifications;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shift.link.sdk.api.vos.responses.verifications.StartVerificationResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.storages.AutomationStorage;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

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
