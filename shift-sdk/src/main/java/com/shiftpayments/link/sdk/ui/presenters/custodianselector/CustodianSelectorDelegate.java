package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

/**
 * Delegation interface for adding custodian accounts.
 *
 * @author Adrian
 */
public interface CustodianSelectorDelegate {
    void onTokensRetrieved(String accessToken, String refreshToken);
}
