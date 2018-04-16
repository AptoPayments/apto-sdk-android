package com.shift.link.sdk.sdk.tasks.users;

import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

import com.shift.link.sdk.api.exceptions.ApiException;
import com.shift.link.sdk.api.vos.datapoints.DataPointList;
import com.shift.link.sdk.api.vos.responses.users.UserResponseVo;
import com.shift.link.sdk.api.wrappers.LinkApiWrapper;
import com.shift.link.sdk.sdk.tasks.LedgeLinkApiTask;
import com.shift.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

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
