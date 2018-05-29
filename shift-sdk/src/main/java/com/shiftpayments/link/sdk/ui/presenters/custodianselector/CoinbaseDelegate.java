package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;

/**
 * Delegation interface with the Coinbase activity.
 *
 * @author Adrian
 */
public interface CoinbaseDelegate {
    void coinbaseTokensRetrieved(String accessToken, String refreshToken);
    void onCoinbaseException(ApiErrorVo exception);
}
