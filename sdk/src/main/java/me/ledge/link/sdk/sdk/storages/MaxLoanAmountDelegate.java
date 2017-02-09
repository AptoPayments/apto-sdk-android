package me.ledge.link.sdk.sdk.storages;

/**
 * Delegation interface for accessing the max loan amount stored in the Config Storage.
 *
 * @author Adrian
 */
public interface MaxLoanAmountDelegate extends ErrorDelegate {

    void maxLoanAmountRetrieved(int maxAmount);

}
