package com.shiftpayments.link.sdk.sdk.tasks.users;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.responses.users.CreateUserResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to create a new user.
 * @author Wijnand
 */
public class CreateUserTask extends ShiftApiTask<Void, Void, CreateUserResponseVo, DataPointList> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public CreateUserTask(DataPointList requestData, ShiftApiWrapper apiWrapper,
                          ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected CreateUserResponseVo callApi() throws ApiException {
        CreateUserResponseVo response = getApiWrapper().createUser(getRequestData());

        if (response != null) {
            getApiWrapper().setBearerToken(response.user_token);
        }

        return response;
    }
}
