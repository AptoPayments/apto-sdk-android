package me.ledge.link.sdk.sdk.tasks.users;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.requests.users.LoginRequestVo;
import me.ledge.link.sdk.api.vos.responses.users.LoginUserResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to login an exisiting user.
 * @author Wijnand
 */
public class LoginUserTask extends LedgeLinkApiTask<Void, Void, LoginUserResponseVo, LoginRequestVo> {

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
    protected LoginUserResponseVo callApi() throws ApiException {
        LoginUserResponseVo response = getApiWrapper().loginUser(getRequestData());

        if (response != null) {
            getApiWrapper().setBearerToken(response.user_token);
        }

        return response;
    }
}
