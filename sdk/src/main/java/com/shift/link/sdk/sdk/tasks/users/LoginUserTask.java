package com.shift.link.sdk.sdk.tasks.users;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.requests.users.LoginRequestVo;
import com.shift.link.sdk.api.vos.responses.users.LoginUserResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shift.link.sdk.sdk.tasks.ShiftApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to login an exisiting user.
 * @author Wijnand
 */
public class LoginUserTask extends ShiftApiTask<Void, Void, LoginUserResponseVo, LoginRequestVo> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public LoginUserTask(LoginRequestVo requestData, ShiftApiWrapper apiWrapper,
                         ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected LoginUserResponseVo callApi() throws ApiException {
        LoginUserResponseVo response = getApiWrapper().loginUser(getRequestData());

        if (response != null) {
            getApiWrapper().setBearerToken(response.user_token);
        }

        return response;
    }
}
