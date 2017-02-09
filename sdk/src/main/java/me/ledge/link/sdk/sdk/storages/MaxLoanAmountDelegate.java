package me.ledge.link.sdk.sdk.storages;

/**
 * Delegation interface for accessing the max loan amount stored in the Config Storage.
 *
 * @author Adrian
 */
public interface MaxLoanAmountDelegate {

    void maxLoanAmountRetrieved(int maxAmount);

    void errorReceived(String error);
}
