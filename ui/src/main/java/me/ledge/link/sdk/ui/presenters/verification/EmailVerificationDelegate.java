package me.ledge.link.sdk.ui.presenters.verification;

import me.ledge.link.sdk.api.vos.responses.verifications.VerificationResponseVo;

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
