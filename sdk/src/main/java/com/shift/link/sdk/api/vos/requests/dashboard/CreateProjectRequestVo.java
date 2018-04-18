package com.shift.link.sdk.api.vos.requests.dashboard;

import com.shift.link.sdk.api.vos.requests.base.UnauthorizedRequestVo;
import com.shift.link.sdk.api.vos.responses.workflow.ActionVo;

/**
 * Request data to create a project.
 * @author Adrian
 */
public class CreateProjectRequestVo extends UnauthorizedRequestVo {

    public String name;
    public String summary;
    public int isolated_project;
    public String language;
    public String logo_url;

    public int primary_color;
    public int secondary_color;
    public String website;

    public int gross_income_min;
    public int gross_income_max;
    public int gross_income_increments;
    public int gross_income_default;

    public String email_verification_source_address;
    public String support_source_address;
    public String tracker_access_token;
    public String tracker_activate;
    public String secondary_auth_credential;
    public ActionVo welcome_screen_action;
}
