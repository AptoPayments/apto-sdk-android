package me.ledge.link.sdk.sdk.tasks.users;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.api.vos.responses.users.CreateUserResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to login an exisiting user.
 * @author Wijnand
 */
public class LoginUserTask extends LedgeLinkApiTask<Void, Void, CreateUserResponseVo, LoginRequestVo> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public LoginUserTask(LoginRequestVo requestData, LinkApiWrapper apiWrapper,
                         ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected CreateUserResponseVo callApi() throws ApiException {
        CreateUserResponseVo response = getApiWrapper().loginUser(getRequestData());

        if (response != null) {
            getApiWrapper().setBearerToken(response.user_token);
        }

        return response;
    }
}
