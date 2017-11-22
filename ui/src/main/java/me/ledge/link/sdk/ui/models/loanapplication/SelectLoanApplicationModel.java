package me.ledge.link.sdk.ui.models.loanapplication;

/**
 * Information about a loan application.
 * @author Adrian
 */
public interface SelectLoanApplicationModel {

    String getProjectName();

    float getLoanAmount();

    String getTimestamp();

    String getApplicationId();

}
