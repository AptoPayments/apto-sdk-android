package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;

/**
 * Delegation interface with the Coinbase activity.
 *
 * @author Adrian
 */
public interface OAuthDelegate {
    String getProvider();
    void oAuthTokensRetrieved(String accessToken, String refreshToken);
    void onOAuthError(ApiErrorVo error);
}
