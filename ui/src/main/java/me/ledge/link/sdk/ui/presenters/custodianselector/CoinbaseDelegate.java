package me.ledge.link.sdk.ui.presenters.custodianselector;

/**
 * Delegation interface with the Coinbase activity.
 *
 * @author Adrian
 */
public interface CoinbaseDelegate {
    void coinbaseTokenRetrieved(String token);
    void onCoinbaseException(Exception exception);
}
