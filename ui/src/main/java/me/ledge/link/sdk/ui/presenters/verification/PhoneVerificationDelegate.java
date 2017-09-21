package me.ledge.link.sdk.ui.presenters.verification;

import me.ledge.link.api.vos.responses.verifications.SimpleVerificationResponseVo;

/**
 * Delegation interface for phone verification.
 *
 * @author Adrian
 */
public interface PhoneVerificationDelegate {

    void phoneVerificationSucceeded(SimpleVerificationResponseVo secondaryCredential);
    void phoneVerificationOnBackPressed();
}
