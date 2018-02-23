package me.ledge.link.sdk.ui.presenters.custodianselector;

/**
 * Delegation interface with the Coinbase activity.
 *
 * @author Adrian
 */
public interface CoinbaseDelegate {
    void coinbaseTokensRetrieved(String accessToken, String refreshToken);
    void onCoinbaseException(Exception exception);
}
