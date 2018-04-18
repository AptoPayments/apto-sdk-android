package com.shift.link.sdk.ui.presenters.verification;

import com.shift.link.sdk.api.vos.responses.verifications.VerificationResponseVo;

/**
 * Delegation interface for phone verification.
 *
 * @author Adrian
 */
public interface PhoneVerificationDelegate {

    void phoneVerificationSucceeded(VerificationResponseVo secondaryCredential);
    void phoneVerificationOnBackPressed();
}
