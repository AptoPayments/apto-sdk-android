package me.ledge.link.sdk.sdk.storages;

/**
 * Delegation interface for accessing the loan amount increments stored in the Config Storage.
 *
 * @author Adrian
 */
public interface LoanAmountIncrementsDelegate extends ErrorDelegate {

    void loanAmountIncrementsRetrieved(int incrementAmount);

}
