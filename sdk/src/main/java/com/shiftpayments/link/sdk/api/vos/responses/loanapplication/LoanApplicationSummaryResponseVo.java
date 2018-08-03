package com.shiftpayments.link.sdk.api.vos.responses.loanapplication;

import com.google.gson.annotations.SerializedName;
import com.shiftpayments.link.sdk.api.utils.loanapplication.LoanApplicationState;

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
     * @see LoanApplicationState
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
