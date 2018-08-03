package com.shiftpayments.link.sdk.sdk.tasks.users;

import com.shiftpayments.link.sdk.api.exceptions.ApiException;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointList;
import com.shiftpayments.link.sdk.api.vos.datapoints.DataPointVo;
import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.vos.requests.users.CurrentUserRequestVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.CurrentUserResponseVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * A concrete {@link ShiftApiTask} to retrieve the current user info.
 * @author Adrian
 */
public class GetCurrentUserTask extends ShiftApiTask<Void, Void, DataPointList, UnauthorizedRequestVo> {

    /**
     * @see ShiftApiTask#ShiftApiTask
     * @param requestData See {@link ShiftApiTask#ShiftApiTask}.
     * @param apiWrapper See {@link ShiftApiTask#ShiftApiTask}.
     * @param responseHandler See {@link ShiftApiTask#ShiftApiTask}.
     */
    public GetCurrentUserTask(CurrentUserRequestVo requestData, ShiftApiWrapper apiWrapper,
                              ApiResponseHandler responseHandler) {

        super(requestData, apiWrapper, responseHandler);
    }

    /** {@inheritDoc} */
    @Override
    protected DataPointList callApi() throws ApiException {
        CurrentUserRequestVo request = (CurrentUserRequestVo) getRequestData();
        CurrentUserResponseVo response = getApiWrapper().getCurrentUser(request, request.throwSessionExpiredError);
        DataPointList dataPointList = new DataPointList(DataPointList.ListType.userData);
        for(DataPointVo d : response.userData.data) {
            if(d != null) {
                dataPointList.add(d);
            }
        }

        return dataPointList;
    }
}
