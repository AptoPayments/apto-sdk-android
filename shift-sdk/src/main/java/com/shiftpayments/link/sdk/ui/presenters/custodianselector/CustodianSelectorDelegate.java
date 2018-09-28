package com.shiftpayments.link.sdk.ui.presenters.custodianselector;

import com.shiftpayments.link.sdk.api.vos.responses.users.OAuthStatusResponseVo;

/**
 * Delegation interface for adding custodian accounts.
 *
 * @author Adrian
 */
public interface CustodianSelectorDelegate {
    void onTokensRetrieved(OAuthStatusResponseVo oAuthResponse);
}
