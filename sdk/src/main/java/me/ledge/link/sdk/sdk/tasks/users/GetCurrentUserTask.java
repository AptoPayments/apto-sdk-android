package me.ledge.link.sdk.sdk.tasks.users;

import me.ledge.link.sdk.api.exceptions.ApiException;
import me.ledge.link.sdk.api.vos.datapoints.DataPointList;
import me.ledge.link.sdk.api.vos.datapoints.DataPointVo;
import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import me.ledge.link.sdk.api.vos.responses.users.CurrentUserResponseVo;
import me.ledge.link.sdk.api.wrappers.LinkApiWrapper;
import me.ledge.link.sdk.sdk.tasks.LedgeLinkApiTask;
import me.ledge.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link LedgeLinkApiTask} to retrieve the current user info.
 * @author Adrian
 */
public class GetCurrentUserTask extends LedgeLinkApiTask<Void, Void, DataPointList, UnauthorizedRequestVo> {

    private final boolean mThrowSessionExpiredError;

    /**
     * @see LedgeLinkApiTask#LedgeLinkApiTask
     * @param requestData See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param apiWrapper See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param responseHandler See {@link LedgeLinkApiTask#LedgeLinkApiTask}.
     * @param throwSessionExpiredError specify if a {@link me.ledge.link.api.vos.responses.SessionExpiredErrorVo} must be thrown
     */
    public GetCurrentUserTask(UnauthorizedRequestVo requestData, LinkApiWrapper apiWrapper,
                              ApiResponseHandler responseHandler, boolean throwSessionExpiredError) {

        super(requestData, apiWrapper, responseHandler);
        this.mThrowSessionExpiredError = throwSessionExpiredError;
    }

    /** {@inheritDoc} */
    @Override
    protected DataPointList callApi() throws ApiException {
        CurrentUserResponseVo response = getApiWrapper().getCurrentUser(getRequestData(), mThrowSessionExpiredError);
        DataPointList dataPointList = new DataPointList();
        for(DataPointVo d : response.userData.data) {
            if(d != null) {
                dataPointList.add(d);
            }
        }

        return dataPointList;
    }
}
