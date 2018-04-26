package com.shiftpayments.link.sdk.ui.presenters.verification;

import com.shiftpayments.link.sdk.api.vos.responses.verifications.VerificationResponseVo;

/**
 * Delegation interface for email verification.
 *
 * @author Adrian
 */
public interface EmailVerificationDelegate {

    void emailVerificationSucceeded(VerificationResponseVo secondaryCredential);
    void emailVerificationOnBackPressed();
    boolean isStartVerificationRequired();

}
