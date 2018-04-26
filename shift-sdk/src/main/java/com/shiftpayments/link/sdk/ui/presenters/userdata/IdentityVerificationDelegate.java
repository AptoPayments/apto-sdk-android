package com.shiftpayments.link.sdk.ui.presenters.userdata;

/**
 * Delegation interface for identity verification.
 *
 * @author Adrian
 */
public interface IdentityVerificationDelegate {

    void identityVerificationSucceeded();
    void identityVerificationOnBackPressed();

}
