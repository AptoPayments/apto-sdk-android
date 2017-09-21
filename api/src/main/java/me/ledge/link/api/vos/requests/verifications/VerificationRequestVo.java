package me.ledge.link.api.vos.requests.verifications;

import me.ledge.link.api.vos.requests.base.UnauthorizedRequestVo;

/**
 * Request data to complete the user's verification.
 * @author Adrian
 */
public class VerificationRequestVo extends UnauthorizedRequestVo {
    /**
     * Secret input from user
     */
    public String secret;

    /**
     * Echo of ID returned in the initial verification request
     */
    public String verification_id;

    /**
     * Ask backend to start verification of secondary credential
     */
    public boolean autostart_secondary_credential_verification;
}
