package me.ledge.link.sdk.sdk.utils.tasks;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.ApiErrorVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

public class RoboLinkApiErrorTask extends LedgeLinkApiTask<Void, Void, Object, Object> {

    public static final String MESSAGE = "message";
    public static final int SERVER_CODE = 666;
    public static final String SERVER_MESSAGE = "server message";

    public RoboLinkApiErrorTask(Object requestData, LinkApiWrapper apiWrapper, ApiResponseHandler responseHandler) {
        super(requestData, apiWrapper, responseHandler);
    }

    @Override
    protected Object callApi() throws ApiException {
        ApiErrorVo error = new ApiErrorVo();
        error.serverCode = SERVER_CODE;
        error.serverMessage = SERVER_MESSAGE;

        throw new ApiException(error, MESSAGE);
    }
}
