package me.ledge.link.sdk.ui.presenters.verification;

import me.ledge.link.api.vos.DataPointVo;

/**
 * Delegation interface for phone verification.
 *
 * @author Adrian
 */
public interface PhoneVerificationDelegate {

    void phoneVerificationSucceeded(DataPointVo phone);
    void phoneVerificationOnBackPressed();
    void noAlternateCredentials();
}
