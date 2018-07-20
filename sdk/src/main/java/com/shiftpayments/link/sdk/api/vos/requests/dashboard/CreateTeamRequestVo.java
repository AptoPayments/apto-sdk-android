package com.shiftpayments.link.sdk.api.vos.requests.dashboard;

import com.shiftpayments.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shiftpayments.link.sdk.api.wrappers.ShiftApiWrapper;
import com.shiftpayments.link.sdk.sdk.tasks.ShiftApiTask;
import com.shiftpayments.link.sdk.sdk.tasks.handlers.ApiResponseHandler;

/**
 * Request data to create a team.
 * @author Adrian
 */
public class CreateTeamRequestVo extends UnauthorizedRequestVo {

    public String name;

    public String website;

    public int isolated_team;

    public int isolated_project;

    @Override
    public ShiftApiTask getApiTask(ShiftApiWrapper shiftApiWrapper, ApiResponseHandler responseHandler) {
        // TODO
        return null;
    }
}
