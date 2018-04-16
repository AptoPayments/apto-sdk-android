package com.shift.link.sdk.api.vos.responses.loanapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Loan application summary API response object.
 * @author Adrian
 */
public class LoanApplicationSummaryResponseVo {

    @SerializedName("type")
    public String type;

    /**
     * Loan application ID.
     */
    @SerializedName("application_id")
    public String id;

    /**
     * Loan application creation time.<br />
     * Time past since Unix epoch.
     */
    public String timestamp;

    /**
     * Loan application status.
     * @see me.ledge.link.api.utils.loanapplication.LoanApplicationState
     */
    public String status;

    @SerializedName("loan_amount")
    public float loanAmount;

    @SerializedName("project_name")
    public String projectName;

    @SerializedName("project_summary")
    public String projectSummary;

    @SerializedName("project_logo")
    public String projectLogo;
}
