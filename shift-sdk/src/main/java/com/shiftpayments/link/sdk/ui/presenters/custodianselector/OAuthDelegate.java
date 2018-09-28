package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import com.shiftpayments.link.sdk.api.vos.responses.ApiErrorVo;
import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;

/**
 * Delegation interface with the Coinbase activity.
 *
 * @author Adrian
 */
public interface OAuthDelegate {
    String getProvider();
    void onOauthPassed(OAuthStatusResponseVo oAuthResponse);
    void onOAuthError(ApiErrorVo error);
}
