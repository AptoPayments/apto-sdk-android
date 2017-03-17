package me.ledge.link.sdk.sdk.tasks.users;

import me.ledge.link.api.exceptions.ApiException;
import me.ledge.link.api.vos.datapoints.DataPointList;
import me.ledge.link.api.vos.responses.users.UserResponseVo;
import me.ledge.link.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to update an existing user.
 * @author Wijnand
 */
public class UpdateUserTask extends LedgeLinkApiTask<Void, Void, UserResponseVo, DataPointList> {

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     */
    public UpdateUserTask(DataPointList requestData, LinkApiWrapper apiWrapper,
                          ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected UserResponseVo callApi() throws ApiException {
        return getApiWrapper().updateUser(getRequestData());
    }
}
