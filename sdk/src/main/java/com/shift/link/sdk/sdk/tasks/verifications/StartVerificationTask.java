package com.shift.link.sdk.sdk.tasks.verifications;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.verifications.StartVerificationRequestVo;
import com.shift.link.sdk.api.vos.responses.verifications.StartVerificationResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.storages.AutomationStorage;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to start a verification.
 * @author Adrian
 */
public class StartVerificationTask extends ShiftApiTask<Void, Void, StartVerificationResponseVo, StartVerificationRequestVo> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public StartVerificationTask(StartVerificationRequestVo requestData, ShiftApiWrapper apiWrapper,
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
