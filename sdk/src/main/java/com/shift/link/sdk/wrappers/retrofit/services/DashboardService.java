package com.shift.link.sdk.wrappers.retrofit.services;

import com.shift.link.sdk.api.vos.requests.dashboard.CreateProjectRequestVo;
import com.shift.link.sdk.api.vos.requests.dashboard.CreateTeamRequestVo;
import com.shift.link.sdk.api.vos.responses.dashboard.CreateProjectResponseVo;
import com.shift.link.sdk.api.vos.responses.dashboard.CreateTeamResponseVo;
import com.shift.link.sdk.api.wrappers.ShiftApiWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Team related API calls.
 * @author Adrian
 */
public interface DashboardService {
    /**
     * Creates a {@link Call} to create a new team.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.CREATE_TEAM_PATH)
    Call<CreateTeamResponseVo> createTeam(@Body CreateTeamRequestVo data);

    /**
     * Creates a {@link Call} to delete a team
     * @param teamId Mandatory request data.
     * @return API call to execute.
     */
    @DELETE(ShiftApiWrapper.DELETE_TEAM_PATH)
    Call<Void> deleteTeam(@Path("TEAM_ID") String teamId);

    /**
     * Creates a {@link Call} to create a new project.
     * @param data Mandatory request data.
     * @return API call to execute.
     */
    @POST(ShiftApiWrapper.CREATE_PROJECT_PATH)
    Call<CreateProjectResponseVo> createProject(@Body CreateProjectRequestVo data, @Path("TEAM_ID") String teamId);

    /**
     * Creates a {@link Call} to delete a project
     * @param teamId Mandatory request data.
     * @param projectId Mandatory request data.
     * @return API call to execute.
     */
    @DELETE(ShiftApiWrapper.DELETE_PROJECT_PATH)
    Call<Void> deleteProject(@Path("TEAM_ID") String teamId, @Path("PROJECT_ID") String projectId);
}
