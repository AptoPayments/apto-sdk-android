package me.ledge.link.sdk.api.vos.requests.dashboard;

import me.ledge.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to create a team.
 * @author Adrian
 */
public class CreateTeamRequestVo extends UnauthorizedRequestVo {

    public String name;

    public String website;

    public int isolated_team;

    public int isolated_project;

}